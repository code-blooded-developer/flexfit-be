#!/usr/bin/env python3
"""Upload exercise demo videos to Supabase Storage and record them in the DB.

Each exercise's video lives at:

    exercise-media/exercises/<exerciseId>/video-v1.mp4

and the row's ``video_key`` is set to the object name (``video-v1.mp4``) once
the upload has been verified. The exercise id is the library id, not the
per-workout row id.

Exercises are processed in the order they appear in ExerciseSeeder.java, so
the "next" exercise is simply the first one without a video_key. No state file
is kept: the cursor is always recoverable from the database.

Usage
-----
    scripts/upload-exercise-video.py --status
        Show progress and what would come next.

    scripts/upload-exercise-video.py --sync
        No uploads. Set video_key for any exercise whose object already
        exists in the bucket (adopts manually uploaded files).

    scripts/upload-exercise-video.py
        Upload the newest video in ~/Downloads to the next exercise.

    scripts/upload-exercise-video.py clip.mp4
        Upload a specific file to the next exercise.

    scripts/upload-exercise-video.py --exercise "Bench Press" clip.mp4
        Upload to one named exercise.

Credentials come from the backend .env (SUPABASE_URL,
SUPABASE_SERVICE_ROLE_KEY, SPRING_DATASOURCE_*). Override the database with
--db-url when you need to write to a deployed instance.
"""

from __future__ import annotations

import argparse
import os
import re
import subprocess
import sys
import time
from concurrent.futures import ThreadPoolExecutor
from pathlib import Path
from urllib.parse import urlparse

import requests

REPO_ROOT = Path(__file__).resolve().parent.parent
SEEDER = REPO_ROOT / "src" / "main" / "java" / "com" / "harish" / "flexfit" / "seeder" / "ExerciseSeeder.java"

DEFAULT_BUCKET = "exercise-media"
DEFAULT_OBJECT = "video-v1.mp4"
DEFAULT_SOURCE_DIR = Path.home() / "Downloads"
OBJECT_PREFIX = "exercises"

VIDEO_EXTS = {".mp4", ".mov", ".m4v", ".webm"}
# Container signature required for a given object extension. Catches truncated
# or partially-downloaded files, which are otherwise uploaded happily and only
# surface as a broken player.
CONTAINER_BY_EXT = {
    ".mp4": "ftyp",
    ".m4v": "ftyp",
    ".mov": "ftyp",
    ".webm": "ebml",
}
CONTENT_TYPE_BY_EXT = {
    ".mp4": "video/mp4",
    ".m4v": "video/mp4",
    ".mov": "video/quicktime",
    ".webm": "video/webm",
}

EXIT_OK = 0
EXIT_ERROR = 1
EXIT_NOTHING_TO_DO = 2


# ── Output helpers ────────────────────────────────────────────────────────────


def say(msg: str = "") -> None:
    print(msg, flush=True)


def fail(msg: str) -> None:
    print(f"error: {msg}", file=sys.stderr, flush=True)
    raise SystemExit(EXIT_ERROR)


def human_size(num_bytes: int) -> str:
    size = float(num_bytes)
    for unit in ("B", "KB", "MB", "GB"):
        if size < 1024 or unit == "GB":
            return f"{size:.1f} {unit}" if unit != "B" else f"{int(size)} B"
        size /= 1024
    return f"{size:.1f} GB"


# ── Configuration ─────────────────────────────────────────────────────────────


def load_env(path: Path) -> dict[str, str]:
    if not path.is_file():
        fail(f"{path} not found — it holds the Supabase and datasource credentials")
    env: dict[str, str] = {}
    for raw in path.read_text(encoding="utf-8").splitlines():
        line = raw.strip()
        if not line or line.startswith("#") or "=" not in line:
            continue
        key, _, value = line.partition("=")
        value = value.strip().strip('"').strip("'")
        env[key.strip()] = value
    return env


def db_config(env: dict[str, str], override: str | None) -> dict[str, str]:
    """Resolves host/port/db/user/password from a jdbc: or postgresql:// URL."""
    raw = override or env.get("SPRING_DATASOURCE_URL", "")
    if not raw:
        fail("SPRING_DATASOURCE_URL is missing from .env (or pass --db-url)")

    if raw.startswith("jdbc:"):
        raw = raw[len("jdbc:"):]

    parsed = urlparse(raw)
    if parsed.scheme not in ("postgresql", "postgres"):
        fail(f"unsupported database URL: {raw}")

    user = parsed.username or env.get("SPRING_DATASOURCE_USERNAME")
    password = parsed.password or env.get("SPRING_DATASOURCE_PASSWORD")
    if not user:
        fail("no database user found (SPRING_DATASOURCE_USERNAME or --db-url)")
    if not password:
        fail("no database password found (SPRING_DATASOURCE_PASSWORD or --db-url)")

    return {
        "host": parsed.hostname or "localhost",
        "port": str(parsed.port or 5432),
        "dbname": (parsed.path or "/").lstrip("/"),
        "user": user,
        "password": password,
    }


class Supabase:
    def __init__(self, env: dict[str, str], bucket: str) -> None:
        self.url = env.get("SUPABASE_URL", "").rstrip("/")
        self.key = env.get("SUPABASE_SERVICE_ROLE_KEY", "")
        self.bucket = bucket
        if not self.url or not self.key:
            fail("SUPABASE_URL and SUPABASE_SERVICE_ROLE_KEY must be set in .env")
        self.auth = {"Authorization": f"Bearer {self.key}"}

    def object_path(self, exercise_id: str, object_name: str) -> str:
        return f"{OBJECT_PREFIX}/{exercise_id}/{object_name}"

    def public_url(self, exercise_id: str, object_name: str) -> str:
        return f"{self.url}/storage/v1/object/public/{self.bucket}/{self.object_path(exercise_id, object_name)}"

    def object_size(self, exercise_id: str, object_name: str) -> int | None:
        """Size of an existing object, or None when it is not there."""
        try:
            response = requests.head(
                self.public_url(exercise_id, object_name), timeout=20
            )
        except requests.RequestException:
            return None
        if response.status_code != 200:
            return None
        length = response.headers.get("Content-Length")
        return int(length) if length else 0

    def upload(self, exercise_id: str, object_name: str, source: Path) -> None:
        path = self.object_path(exercise_id, object_name)
        content_type = CONTENT_TYPE_BY_EXT.get(source.suffix.lower(), "video/mp4")
        headers = {**self.auth, "Content-Type": content_type, "x-upsert": "true"}
        with source.open("rb") as handle:
            response = requests.post(
                f"{self.url}/storage/v1/object/{self.bucket}/{path}",
                headers=headers,
                data=handle,
                timeout=300,
            )
        if response.status_code not in (200, 201):
            fail(
                f"upload failed ({response.status_code}) for {path}: "
                f"{response.text[:300]}"
            )


# ── Database ──────────────────────────────────────────────────────────────────


def run_psql(conn: dict[str, str], sql: str, tuples_only: bool = False) -> str:
    cmd = ["psql", "-h", conn["host"], "-p", conn["port"], "-U", conn["user"],
           "-d", conn["dbname"], "-v", "ON_ERROR_STOP=1", "-c", sql]
    if tuples_only:
        cmd.insert(1, "-At")
    result = subprocess.run(
        cmd,
        capture_output=True,
        text=True,
        env={**os.environ, "PGPASSWORD": conn["password"]},
    )
    if result.returncode != 0:
        fail(f"psql failed: {result.stderr.strip()[:400]}")
    return result.stdout


class Exercise:
    __slots__ = ("name", "id", "video_key")

    def __init__(self, name: str, exercise_id: str, video_key: str | None) -> None:
        self.name = name
        self.id = exercise_id
        self.video_key = video_key


def seeder_order() -> list[str]:
    """Exercise names in the order the seeder declares them."""
    if not SEEDER.is_file():
        fail(f"cannot find {SEEDER}")
    source = SEEDER.read_text(encoding="utf-8")
    names = re.findall(r'create\("([^"]+)"', source)
    if not names:
        fail(f"no create(\"...\") calls parsed out of {SEEDER.name}")
    return names


def load_exercises(conn: dict[str, str], order: list[str]) -> list[Exercise]:
    output = run_psql(
        conn,
        "select id, name, coalesce(video_key, '') from exercises",
        tuples_only=True,
    )
    by_name: dict[str, Exercise] = {}
    for line in output.splitlines():
        if not line.strip():
            continue
        exercise_id, name, video_key = line.split("|", 2)
        by_name[name] = Exercise(name, exercise_id, video_key or None)

    missing = [name for name in order if name not in by_name]
    if missing:
        fail(
            f"{len(missing)} seeder exercise(s) are not in the database, "
            f"e.g. {', '.join(missing[:3])} — run the seeder first"
        )
    return [by_name[name] for name in order]


def set_video_key(conn: dict[str, str], exercise_id: str, object_name: str) -> None:
    if not re.fullmatch(r"[0-9a-fA-F-]{36}", exercise_id):
        fail(f"refusing to update a non-UUID id: {exercise_id!r}")
    output = run_psql(
        conn,
        f"update exercises set video_key = '{object_name}' "
        f"where id = '{exercise_id}' returning id",
        tuples_only=True,
    )
    if not output.strip():
        fail(f"no row updated for exercise {exercise_id}")


# ── Source file selection ─────────────────────────────────────────────────────


def check_container(source: Path, object_name: str) -> None:
    expected = CONTAINER_BY_EXT.get(Path(object_name).suffix.lower())
    if not expected:
        fail(f"unsupported object name {object_name!r} (use .mp4, .mov or .webm)")

    with source.open("rb") as handle:
        head = handle.read(12)

    is_ftyp = len(head) >= 8 and head[4:8] == b"ftyp"
    is_ebml = head[:4] == b"\x1a\xe4\xdf\xa3"
    actual = "ftyp" if is_ftyp else "ebml" if is_ebml else None

    if actual is None:
        fail(
            f"{source.name} is not a recognisable video container — "
            "the download may be incomplete or corrupt"
        )
    if actual != expected:
        fail(
            f"{source.name} is a {actual} container but the object name "
            f"{object_name!r} expects {expected} — pass --object-name to match"
        )


def pick_source(explicit: str | None, source_dir: Path) -> Path:
    if explicit:
        path = Path(explicit).expanduser()
        if not path.is_file():
            fail(f"file not found: {path}")
        return path

    if not source_dir.is_dir():
        fail(f"source directory not found: {source_dir}")

    candidates = [
        path
        for path in source_dir.iterdir()
        if path.is_file() and path.suffix.lower() in VIDEO_EXTS
    ]
    if not candidates:
        fail(f"no video files in {source_dir} — pass a file path explicitly")

    return max(candidates, key=lambda path: path.stat().st_mtime)


# ── Commands ──────────────────────────────────────────────────────────────────


def print_status(exercises: list[Exercise], count: int) -> None:
    done = sum(1 for exercise in exercises if exercise.video_key)
    total = len(exercises)
    pending = [exercise for exercise in exercises if not exercise.video_key]

    say(f"Videos uploaded: {done} / {total}  ({done / total * 100:.1f}%)")

    if not pending:
        say("All exercises have a video.")
        return

    say(f"Next {min(count, len(pending))} in seeder order:")
    for index, exercise in enumerate(pending[:count], start=1):
        position = exercises.index(exercise) + 1
        say(f"  {index}. {exercise.name}  (seeder #{position})")


def command_sync(
    exercises: list[Exercise],
    supabase: Supabase,
    conn: dict[str, str],
    object_name: str,
    dry_run: bool,
) -> int:
    pending = [exercise for exercise in exercises if not exercise.video_key]
    if not pending:
        say("Nothing to sync — every exercise already has a video_key.")
        return EXIT_NOTHING_TO_DO

    say(f"Checking {len(pending)} exercise(s) without a video_key...")
    with ThreadPoolExecutor(max_workers=8) as pool:
        sizes = list(
            pool.map(lambda ex: supabase.object_size(ex.id, object_name), pending)
        )

    adoptable = [
        (exercise, size)
        for exercise, size in zip(pending, sizes)
        if size is not None
    ]

    if not adoptable:
        say("No matching objects in the bucket — nothing to adopt.")
        return EXIT_NOTHING_TO_DO

    for exercise, size in adoptable:
        if dry_run:
            say(f"  would set video_key on {exercise.name} ({human_size(size)})")
            continue
        set_video_key(conn, exercise.id, object_name)
        say(f"  adopted {exercise.name} ({human_size(size)})")

    if dry_run:
        say(f"dry run: {len(adoptable)} exercise(s) would be adopted")
    else:
        say(f"Adopted {len(adoptable)} exercise(s) from existing bucket objects.")
    return EXIT_OK


def command_upload(
    exercises: list[Exercise],
    supabase: Supabase,
    conn: dict[str, str],
    args: argparse.Namespace,
) -> int:
    if args.exercise:
        match = next(
            (ex for ex in exercises if ex.name.lower() == args.exercise.lower()), None
        )
        if match is None:
            close = [ex.name for ex in exercises if args.exercise.lower() in ex.name.lower()]
            hint = f" — did you mean: {', '.join(close[:5])}?" if close else ""
            fail(f"no exercise named {args.exercise!r}{hint}")
        target = match
        position = exercises.index(target) + 1
        if target.video_key and not args.force:
            fail(
                f"{target.name} already has video_key = {target.video_key} "
                "(pass --force to replace it)"
            )
    else:
        pending = [ex for ex in exercises if not ex.video_key]
        if not pending:
            say("All exercises have a video.")
            return EXIT_NOTHING_TO_DO
        target = pending[0]
        position = exercises.index(target) + 1

    source = pick_source(args.file, Path(args.source_dir).expanduser())
    check_container(source, args.object_name)
    size = source.stat().st_size
    if size == 0:
        fail(f"{source.name} is empty")

    done = sum(1 for exercise in exercises if exercise.video_key)
    total = len(exercises)

    say(f"Exercise:   {target.name}  (seeder #{position})")
    say(f"Object:     {supabase.object_path(target.id, args.object_name)}")
    say(f"Source:     {source}  ({human_size(size)})")
    if target.video_key:
        say(f"Replacing:  {target.video_key}")

    if args.dry_run:
        say("dry run: nothing uploaded")
        return EXIT_OK

    if not args.yes:
        answer = input("Upload and set video_key? [y/N] ").strip().lower()
        if answer not in ("y", "yes"):
            say("aborted")
            return EXIT_ERROR

    supabase.upload(target.id, args.object_name, source)
    say("  uploaded")

    # The DB must not claim a video that isn't fully there.
    uploaded_size = None
    for attempt in range(5):
        uploaded_size = supabase.object_size(target.id, args.object_name)
        if uploaded_size == size:
            break
        time.sleep(1.5 * (attempt + 1))

    if uploaded_size != size:
        fail(
            f"size mismatch after upload: local {size} bytes, "
            f"remote {uploaded_size} — video_key left unchanged"
        )
    say(f"  verified {human_size(uploaded_size)}")

    set_video_key(conn, target.id, args.object_name)
    say(f"  video_key = {args.object_name}")

    say(f"Done: {done + 1} / {total} exercises now have a video.")
    return EXIT_OK


# ── Entry point ───────────────────────────────────────────────────────────────


def parse_args(argv: list[str]) -> argparse.Namespace:
    parser = argparse.ArgumentParser(
        description="Upload exercise demo videos to Supabase Storage and record video_key.",
        formatter_class=argparse.RawDescriptionHelpFormatter,
        epilog=__doc__,
    )
    parser.add_argument(
        "file",
        nargs="?",
        help="video file to upload (default: newest video in --source-dir)",
    )
    parser.add_argument(
        "--exercise",
        metavar="NAME",
        help="upload to this exercise instead of the next one in seeder order",
    )
    parser.add_argument(
        "--sync",
        action="store_true",
        help="set video_key for exercises whose object already exists; no uploads",
    )
    parser.add_argument(
        "--status", action="store_true", help="show progress and exit"
    )
    parser.add_argument(
        "--count",
        type=int,
        default=3,
        metavar="N",
        help="how many upcoming exercises --status lists (default: 3)",
    )
    parser.add_argument(
        "--source-dir",
        default=str(DEFAULT_SOURCE_DIR),
        metavar="DIR",
        help=f"where to look for videos (default: {DEFAULT_SOURCE_DIR})",
    )
    parser.add_argument(
        "--bucket", default=DEFAULT_BUCKET, help=f"storage bucket (default: {DEFAULT_BUCKET})"
    )
    parser.add_argument(
        "--object-name",
        default=DEFAULT_OBJECT,
        help=f"stored file name (default: {DEFAULT_OBJECT})",
    )
    parser.add_argument(
        "--db-url",
        metavar="URL",
        help="override the database URL from .env (jdbc: or postgresql://)",
    )
    parser.add_argument(
        "--env-file",
        default=str(REPO_ROOT / ".env"),
        metavar="PATH",
        help="env file to read credentials from (default: .env in the repo root)",
    )
    parser.add_argument("--force", action="store_true", help="replace an existing video")
    parser.add_argument("--yes", "-y", action="store_true", help="skip confirmation")
    parser.add_argument(
        "--dry-run", action="store_true", help="show what would happen, change nothing"
    )
    return parser.parse_args(argv)


def main(argv: list[str]) -> int:
    args = parse_args(argv)
    env = load_env(Path(args.env_file).expanduser())
    conn = db_config(env, args.db_url)
    supabase = Supabase(env, args.bucket)

    exercises = load_exercises(conn, seeder_order())

    if args.status:
        print_status(exercises, args.count)
        return EXIT_OK

    if args.sync:
        return command_sync(
            exercises, supabase, conn, args.object_name, args.dry_run
        )

    return command_upload(exercises, supabase, conn, args)


if __name__ == "__main__":
    try:
        raise SystemExit(main(sys.argv[1:]))
    except KeyboardInterrupt:
        say("\naborted")
        raise SystemExit(EXIT_ERROR)
