package com.harish.flexfit.service.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.harish.flexfit.dto.request.AddExerciseRequest;
import com.harish.flexfit.dto.request.LogSetRequest;

import com.harish.flexfit.dto.response.WorkoutExerciseResponse;
import com.harish.flexfit.dto.response.WorkoutResponse;
import com.harish.flexfit.dto.response.WorkoutSetResponse;
import com.harish.flexfit.entity.Equipment;
import com.harish.flexfit.entity.Exercise;
import com.harish.flexfit.entity.UserProfile;
import com.harish.flexfit.entity.Workout;
import com.harish.flexfit.entity.WorkoutExercise;
import com.harish.flexfit.entity.WorkoutSet;
import com.harish.flexfit.enums.DifficultyLevel;
import com.harish.flexfit.enums.ExerciseCategory;
import com.harish.flexfit.enums.ExerciseType;
import com.harish.flexfit.enums.ExperienceLevel;
import com.harish.flexfit.enums.FitnessGoal;
import com.harish.flexfit.enums.MuscleGroup;
import com.harish.flexfit.enums.WorkoutSplit;
import com.harish.flexfit.enums.WorkoutStatus;
import com.harish.flexfit.exception.BadRequestException;
import com.harish.flexfit.exception.ResourceNotFoundException;
import com.harish.flexfit.repo.ExerciseRepository;
import com.harish.flexfit.repo.UserProfileRepository;
import com.harish.flexfit.repo.WorkoutExerciseRepository;
import com.harish.flexfit.repo.WorkoutRepository;
import com.harish.flexfit.repo.WorkoutSetRepository;
import com.harish.flexfit.service.WorkoutService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class WorkoutServiceImpl implements WorkoutService {

    @Autowired
    private WorkoutRepository workoutRepository;

    @Autowired
    private WorkoutExerciseRepository workoutExerciseRepository;

    @Autowired
    private WorkoutSetRepository workoutSetRepository;

    @Autowired
    private ExerciseRepository exerciseRepository;

    @Autowired
    private UserProfileRepository userProfileRepository;

    private static final int HISTORY_DAYS = 7;
    private static final int WARMUP_EXERCISE_COUNT = 4;
    private static final int STRETCH_EXERCISE_COUNT = 4;
    private static final int EXERCISE_EXCLUSION_DAYS = 7;

    @Override
    @Transactional
    public WorkoutResponse getTodayWorkout(UUID userId) {
        log.info("Getting today's workout for userId={}", userId);

        UserProfile profile = getProfile(userId);
        LocalDate today = LocalDate.now();

        Workout existing = workoutRepository
                .findByUserProfile_IdAndDate(profile.getId(), today)
                .orElse(null);

        if (existing != null) {
            return map(existing);
        }

        Workout workout = generateWorkout(profile);
        log.info("Generated today's workout for userId={}", userId);
        return map(workout);
    }

    @Override
    @Transactional(readOnly = true)
    public WorkoutResponse getWorkoutById(UUID userId, UUID workoutId) {
        log.info("Fetching workout id={} for userId={}", workoutId, userId);
        UserProfile profile = getProfile(userId);
        Workout workout = workoutRepository.findById(workoutId)
                .orElseThrow(() -> new ResourceNotFoundException("Workout not found"));
        if (!workout.getUserProfile().getId().equals(profile.getId())) {
            throw new BadRequestException("Workout does not belong to this user");
        }
        return map(workout);
    }

    @Override
    @Transactional(readOnly = true)
    public List<WorkoutResponse> getWorkoutHistory(UUID userId) {
        log.info("Fetching workout history for userId={}", userId);
        UserProfile profile = getProfile(userId);
        LocalDate start = LocalDate.now().minusDays(HISTORY_DAYS);
        List<Workout> workouts = workoutRepository
                .findByUserProfile_IdAndDateBetweenOrderByDateDesc(profile.getId(), start, LocalDate.now());
        return workouts.stream().map(this::map).toList();
    }

    @Override
    @Transactional
    public WorkoutResponse completeWorkout(UUID userId, UUID workoutId) {
        log.info("Completing workout id={} for userId={}", workoutId, userId);
        Workout workout = getOwnedWorkout(userId, workoutId);
        workout.setStatus(WorkoutStatus.COMPLETED);
        return map(workoutRepository.save(workout));
    }

    @Override
    @Transactional
    public WorkoutResponse skipWorkout(UUID userId, UUID workoutId) {
        log.info("Skipping workout id={} for userId={}", workoutId, userId);
        Workout workout = getOwnedWorkout(userId, workoutId);
        workout.setStatus(WorkoutStatus.SKIPPED);
        return map(workoutRepository.save(workout));
    }

    @Override
    @Transactional
    public WorkoutResponse addExercise(UUID userId, UUID workoutId, AddExerciseRequest request) {
        log.info("Adding exercise to workout id={} for userId={}", workoutId, userId);
        Workout workout = getOwnedWorkout(userId, workoutId);

        Exercise exercise = exerciseRepository.findById(request.getExerciseId())
                .orElseThrow(() -> new ResourceNotFoundException("Exercise not found"));

        if (exercise.getCategory() != request.getPhase()) {
            throw new BadRequestException(
                    "Exercise category does not match requested phase");
        }

        UserProfile profile = workout.getUserProfile();
        validateEquipmentCompatibility(exercise, profile);

        validateNotUsedRecently(profile.getId(), exercise.getId());

        if (isExerciseInWorkout(workout, exercise.getId())) {
            throw new BadRequestException("Exercise already exists in this workout");
        }

        int maxOrder = workout.getExercises().stream()
                .filter(e -> e.getPhase() == request.getPhase())
                .mapToInt(WorkoutExercise::getSortOrder)
                .max()
                .orElse(0);

        WorkoutExercise we = new WorkoutExercise();
        we.setWorkout(workout);
        we.setExercise(exercise);
        we.setPhase(request.getPhase());
        we.setSortOrder(maxOrder + 1);
        we.setTargetSets(request.getTargetSets() != null ? request.getTargetSets() : getDefaultSets(profile.getFitnessGoal()));
        we.setTargetReps(request.getTargetReps() != null ? request.getTargetReps() : getDefaultReps(profile.getFitnessGoal()));
        we.setRestSeconds(request.getRestSeconds() != null ? request.getRestSeconds() : getDefaultRest(profile.getFitnessGoal()));
        we.setNotes(request.getNotes());
        workout.getExercises().add(we);

        return map(workoutRepository.save(workout));
    }

    @Override
    @Transactional
    public WorkoutResponse removeExercise(UUID userId, UUID workoutId, UUID exerciseId) {
        log.info("Removing exercise id={} from workout id={}", exerciseId, workoutId);
        Workout workout = getOwnedWorkout(userId, workoutId);

        WorkoutExercise we = workout.getExercises().stream()
                .filter(e -> e.getId().equals(exerciseId))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Exercise not found in workout"));

        workout.getExercises().remove(we);

        int order = 0;
        for (WorkoutExercise e : workout.getExercises().stream()
                .filter(e -> e.getPhase() == we.getPhase())
                .sorted((a, b) -> Integer.compare(a.getSortOrder(), b.getSortOrder()))
                .toList()) {
            e.setSortOrder(order++);
        }

        return map(workoutRepository.save(workout));
    }

    @Override
    @Transactional(readOnly = true)
    public List<WorkoutExerciseResponse> getAlternativeExercises(
            UUID userId, UUID workoutId, UUID exerciseId) {
        log.info("Getting alternatives for exercise id={} in workout id={}", exerciseId, workoutId);
        Workout workout = getOwnedWorkout(userId, workoutId);

        WorkoutExercise we = workout.getExercises().stream()
                .filter(e -> e.getId().equals(exerciseId))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Exercise not found in workout"));

        UserProfile profile = workout.getUserProfile();
        Set<UUID> recentExerciseIds = getRecentExerciseIds(profile.getId());
        Set<UUID> currentExerciseIds = workout.getExercises().stream()
                .map(e -> e.getExercise().getId())
                .collect(Collectors.toSet());

        List<Exercise> candidates = findSuitableExercises(
                we.getPhase(),
                we.getExercise().getPrimaryMuscle(),
                profile);

        return candidates.stream()
                .filter(e -> !recentExerciseIds.contains(e.getId()))
                .filter(e -> !currentExerciseIds.contains(e.getId()))
                .filter(e -> !e.getId().equals(we.getExercise().getId()))
                .map(e -> WorkoutExerciseResponse.builder()
                        .exerciseId(e.getId())
                        .exerciseName(e.getName())
                        .primaryMuscle(e.getPrimaryMuscle())
                        .secondaryMuscles(e.getSecondaryMuscles())
                        .exerciseType(e.getExerciseType())
                        .phase(we.getPhase())
                        .mediaUrl(e.getMediaUrl())
                        .build())
                .toList();
    }

    @Override
    @Transactional
    public WorkoutResponse replaceExercise(UUID userId, UUID workoutId,
            UUID exerciseId, UUID alternativeExerciseId) {
        log.info("Replacing exercise id={} with id={} in workout id={}",
                exerciseId, alternativeExerciseId, workoutId);
        Workout workout = getOwnedWorkout(userId, workoutId);

        WorkoutExercise we = workout.getExercises().stream()
                .filter(e -> e.getId().equals(exerciseId))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Exercise not found in workout"));

        Exercise alternative = exerciseRepository.findById(alternativeExerciseId)
                .orElseThrow(() -> new ResourceNotFoundException("Alternative exercise not found"));

        UserProfile profile = workout.getUserProfile();
        validateEquipmentCompatibility(alternative, profile);
        validateNotUsedRecently(profile.getId(), alternative.getId());

        if (isExerciseInWorkout(workout, alternative.getId())) {
            throw new BadRequestException("Alternative exercise already exists in this workout");
        }

        we.setExercise(alternative);
        we.getSets().clear();

        return map(workoutRepository.save(workout));
    }

    @Override
    @Transactional
    public WorkoutSetResponse logSet(UUID userId, UUID workoutId, UUID exerciseId, LogSetRequest request) {
        log.info("Logging set for exercise id={} in workout id={}", exerciseId, workoutId);
        WorkoutExercise we = getOwnedWorkoutExercise(userId, workoutId, exerciseId);

        WorkoutSet set = new WorkoutSet();
        set.setWorkoutExercise(we);
        set.setSetNumber(request.getSetNumber());
        set.setWeight(request.getWeight());
        set.setReps(request.getReps());
        set.setDistance(request.getDistance());
        set.setDurationSeconds(request.getDurationSeconds());
        set.setNotes(request.getNotes());
        we.getSets().add(set);

        workoutSetRepository.save(set);
        log.info("Set logged for exercise id={}", exerciseId);
        return mapSet(set);
    }

    @Override
    @Transactional
    public WorkoutSetResponse updateSet(UUID userId, UUID workoutId,
            UUID exerciseId, UUID setId, LogSetRequest request) {
        log.info("Updating set id={} for exercise id={}", setId, exerciseId);
        WorkoutExercise we = getOwnedWorkoutExercise(userId, workoutId, exerciseId);

        WorkoutSet set = we.getSets().stream()
                .filter(s -> s.getId().equals(setId))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Set not found"));

        set.setSetNumber(request.getSetNumber());
        set.setWeight(request.getWeight());
        set.setReps(request.getReps());
        set.setDistance(request.getDistance());
        set.setDurationSeconds(request.getDurationSeconds());
        set.setNotes(request.getNotes());

        workoutSetRepository.save(set);
        log.info("Set id={} updated", setId);
        return mapSet(set);
    }

    @Override
    @Transactional
    public void deleteSet(UUID userId, UUID workoutId, UUID exerciseId, UUID setId) {
        log.info("Deleting set id={} from exercise id={}", setId, exerciseId);
        WorkoutExercise we = getOwnedWorkoutExercise(userId, workoutId, exerciseId);

        WorkoutSet set = we.getSets().stream()
                .filter(s -> s.getId().equals(setId))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Set not found"));

        we.getSets().remove(set);
        workoutSetRepository.delete(set);
        log.info("Set id={} deleted", setId);
    }

    private Workout generateWorkout(UserProfile profile) {
        log.info("Generating workout for profile id={}", profile.getId());

        List<MuscleGroup> targetMuscles = determineTargetMuscles(profile);
        Set<UUID> recentExerciseIds = getRecentExerciseIds(profile.getId());
        int totalMin = profile.getWorkoutDurationMinutes();

        Workout workout = new Workout();
        workout.setUserProfile(profile);
        workout.setDate(LocalDate.now());
        workout.setTotalDurationMinutes(totalMin);
        workout.setStatus(WorkoutStatus.PLANNED);

        List<WorkoutExercise> exercises = new ArrayList<>();
        int order = 0;

        List<Exercise> warmupPool = findSuitableExercises(
                ExerciseCategory.WARMUP, null, profile);
        warmupPool.removeIf(e -> recentExerciseIds.contains(e.getId()));
        List<Exercise> stretchPool = findSuitableExercises(
                ExerciseCategory.STRETCHING, null, profile);
        stretchPool.removeIf(e -> recentExerciseIds.contains(e.getId()));

        List<Exercise> selectedWarmup = pickRandom(warmupPool, WARMUP_EXERCISE_COUNT);
        for (Exercise e : selectedWarmup) {
            exercises.add(createExerciseEntry(workout, e,
                    ExerciseCategory.WARMUP, order++, 1, "10-15", 0));
        }

        for (MuscleGroup muscle : targetMuscles) {
            List<Exercise> mainPool = findSuitableExercises(
                    ExerciseCategory.MAIN, muscle, profile);
            mainPool.removeIf(e -> recentExerciseIds.contains(e.getId()));

            List<Exercise> compounds = mainPool.stream()
                    .filter(e -> e.getExerciseType() == ExerciseType.COMPOUND)
                    .collect(Collectors.toList());
            List<Exercise> isolations = mainPool.stream()
                    .filter(e -> e.getExerciseType() == ExerciseType.ISOLATION)
                    .collect(Collectors.toList());

            List<Exercise> selected = new ArrayList<>();
            selected.addAll(pickRandom(compounds, 1));
            selected.addAll(pickRandom(isolations, 1));
            selected = selected.stream().distinct().collect(Collectors.toList());

            int sets = getDefaultSets(profile.getFitnessGoal());
            String reps = getDefaultReps(profile.getFitnessGoal());
            int rest = getDefaultRest(profile.getFitnessGoal());

            for (Exercise e : selected) {
                if (!recentExerciseIds.contains(e.getId())) {
                    exercises.add(createExerciseEntry(workout, e,
                            ExerciseCategory.MAIN, order++, sets, reps, rest));
                }
            }
        }

        List<Exercise> selectedStretch = pickRandom(stretchPool, STRETCH_EXERCISE_COUNT);
        for (Exercise e : selectedStretch) {
            exercises.add(createExerciseEntry(workout, e,
                    ExerciseCategory.STRETCHING, order++, 1, "20-30s", 0));
        }

        workout.setExercises(exercises);
        return workoutRepository.save(workout);
    }

    private List<MuscleGroup> determineTargetMuscles(UserProfile profile) {
        WorkoutSplit split = profile.getWorkoutSplit();
        if (split == null) split = WorkoutSplit.FULL_BODY;

        long workoutCount = workoutRepository.countByUserProfile_Id(profile.getId());
        int dayIndex = (int) (workoutCount % getSplitLength(split));

        return switch (split) {
            case FULL_BODY -> List.of(
                    MuscleGroup.CHEST, MuscleGroup.BACK, MuscleGroup.LEGS,
                    MuscleGroup.SHOULDERS, MuscleGroup.BICEPS,
                    MuscleGroup.TRICEPS, MuscleGroup.CORE);
            case UPPER_LOWER -> dayIndex == 0
                    ? List.of(MuscleGroup.CHEST, MuscleGroup.BACK,
                            MuscleGroup.SHOULDERS, MuscleGroup.BICEPS, MuscleGroup.TRICEPS)
                    : List.of(MuscleGroup.LEGS, MuscleGroup.GLUTES,
                            MuscleGroup.HAMSTRINGS, MuscleGroup.CALVES, MuscleGroup.CORE);
            case PUSH_PULL_LEGS -> switch (dayIndex) {
                case 0 -> List.of(MuscleGroup.CHEST, MuscleGroup.SHOULDERS, MuscleGroup.TRICEPS);
                case 1 -> List.of(MuscleGroup.BACK, MuscleGroup.BICEPS, MuscleGroup.HAMSTRINGS);
                default -> List.of(MuscleGroup.LEGS, MuscleGroup.GLUTES,
                        MuscleGroup.CALVES, MuscleGroup.CORE);
            };
            case BRO_SPLIT -> switch (dayIndex) {
                case 0 -> List.of(MuscleGroup.CHEST, MuscleGroup.TRICEPS);
                case 1 -> List.of(MuscleGroup.BACK, MuscleGroup.BICEPS);
                case 2 -> List.of(MuscleGroup.LEGS, MuscleGroup.CORE);
                case 3 -> List.of(MuscleGroup.SHOULDERS);
                default -> List.of(MuscleGroup.BICEPS, MuscleGroup.TRICEPS, MuscleGroup.CORE);
            };
        };
    }

    private int getSplitLength(WorkoutSplit split) {
        return switch (split) {
            case FULL_BODY -> 1;
            case UPPER_LOWER -> 2;
            case PUSH_PULL_LEGS -> 3;
            case BRO_SPLIT -> 5;
        };
    }

    private List<Exercise> findSuitableExercises(
            ExerciseCategory category, MuscleGroup muscle, UserProfile profile) {
        Set<UUID> userEquipmentIds = getUserEquipmentIds(profile);
        Set<DifficultyLevel> allowedDifficulties = getAllowedDifficulties(profile.getExperienceLevel());

        return exerciseRepository.findAll().stream()
                .filter(e -> e.getCategory() == category)
                .filter(e -> muscle == null || e.getPrimaryMuscle() == muscle)
                .filter(e -> allowedDifficulties.contains(e.getDifficultyLevel()))
                .filter(e -> isEquipmentCompatible(e, userEquipmentIds))
                .collect(Collectors.toList());
    }

    private Set<DifficultyLevel> getAllowedDifficulties(ExperienceLevel experience) {
        return switch (experience) {
            case BEGINNER -> Set.of(DifficultyLevel.BEGINNER);
            case INTERMEDIATE -> Set.of(DifficultyLevel.BEGINNER, DifficultyLevel.INTERMEDIATE);
            case ADVANCED -> Set.of(DifficultyLevel.BEGINNER, DifficultyLevel.INTERMEDIATE, DifficultyLevel.ADVANCED);
        };
    }

    private boolean isEquipmentCompatible(Exercise exercise, Set<UUID> userEquipmentIds) {
        List<Equipment> required = exercise.getEquipments();
        if (required == null || required.isEmpty()) {
            return true;
        }
        return required.stream().allMatch(eq -> userEquipmentIds.contains(eq.getId()));
    }

    private void validateEquipmentCompatibility(Exercise exercise, UserProfile profile) {
        Set<UUID> userEquipmentIds = getUserEquipmentIds(profile);
        if (!isEquipmentCompatible(exercise, userEquipmentIds)) {
            throw new BadRequestException(
                    "Exercise requires equipment you don't own: " + exercise.getName());
        }
    }

    private Set<UUID> getUserEquipmentIds(UserProfile profile) {
        if (profile.getUserEquipments() == null) {
            return Collections.emptySet();
        }
        return profile.getUserEquipments().stream()
                .map(ue -> ue.getEquipment().getId())
                .collect(Collectors.toSet());
    }

    private Set<UUID> getRecentExerciseIds(UUID userProfileId) {
        LocalDate since = LocalDate.now().minusDays(EXERCISE_EXCLUSION_DAYS);
        return workoutExerciseRepository
                .findDistinctExercisesByUserProfileSince(userProfileId, since)
                .stream()
                .map(Exercise::getId)
                .collect(Collectors.toSet());
    }

    private void validateNotUsedRecently(UUID userProfileId, UUID exerciseId) {
        LocalDate since = LocalDate.now().minusDays(EXERCISE_EXCLUSION_DAYS);
        List<Exercise> recent = workoutExerciseRepository
                .findDistinctExercisesByUserProfileSince(userProfileId, since);
        boolean used = recent.stream().anyMatch(e -> e.getId().equals(exerciseId));
        if (used) {
            throw new BadRequestException(
                    "Exercise was used in the last " + EXERCISE_EXCLUSION_DAYS + " days");
        }
    }

    private boolean isExerciseInWorkout(Workout workout, UUID exerciseId) {
        return workout.getExercises().stream()
                .anyMatch(e -> e.getExercise().getId().equals(exerciseId));
    }

    private WorkoutExercise createExerciseEntry(
            Workout workout, Exercise exercise, ExerciseCategory phase,
            int order, int sets, String reps, int rest) {
        WorkoutExercise we = new WorkoutExercise();
        we.setWorkout(workout);
        we.setExercise(exercise);
        we.setPhase(phase);
        we.setSortOrder(order);
        we.setTargetSets(sets);
        we.setTargetReps(reps);
        we.setRestSeconds(rest);
        return we;
    }

    private int getDefaultSets(FitnessGoal goal) {
        if (goal == null) return 3;
        return switch (goal) {
            case GAIN_MUSCLE -> 4;
            case LOSE_WEIGHT -> 3;
            case MAINTAIN_FITNESS -> 3;
            case RECOMPOSITION -> 3;
        };
    }

    private String getDefaultReps(FitnessGoal goal) {
        if (goal == null) return "10-12";
        return switch (goal) {
            case GAIN_MUSCLE -> "8-12";
            case LOSE_WEIGHT -> "12-15";
            case MAINTAIN_FITNESS -> "10-12";
            case RECOMPOSITION -> "10-12";
        };
    }

    private int getDefaultRest(FitnessGoal goal) {
        if (goal == null) return 60;
        return switch (goal) {
            case GAIN_MUSCLE -> 75;
            case LOSE_WEIGHT -> 40;
            case MAINTAIN_FITNESS -> 60;
            case RECOMPOSITION -> 60;
        };
    }

    private List<Exercise> pickRandom(List<Exercise> exercises, int count) {
        if (exercises.isEmpty()) return List.of();
        List<Exercise> shuffled = new ArrayList<>(exercises);
        Collections.shuffle(shuffled);
        return shuffled.subList(0, Math.min(count, shuffled.size()));
    }

    private UserProfile getProfile(UUID userId) {
        return userProfileRepository.findByUser_Id(userId)
                .orElseThrow(() -> new BadRequestException("Profile not found. Complete your profile first."));
    }

    private Workout getOwnedWorkout(UUID userId, UUID workoutId) {
        UserProfile profile = getProfile(userId);
        Workout workout = workoutRepository.findById(workoutId)
                .orElseThrow(() -> new ResourceNotFoundException("Workout not found"));
        if (!workout.getUserProfile().getId().equals(profile.getId())) {
            throw new BadRequestException("Workout does not belong to this user");
        }
        return workout;
    }

    private WorkoutExercise getOwnedWorkoutExercise(
            UUID userId, UUID workoutId, UUID exerciseId) {
        Workout workout = getOwnedWorkout(userId, workoutId);
        return workout.getExercises().stream()
                .filter(e -> e.getId().equals(exerciseId))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Exercise not found in workout"));
    }

    private WorkoutResponse map(Workout workout) {
        List<WorkoutExerciseResponse> exerciseResponses = workout.getExercises().stream()
                .sorted((a, b) -> {
                    int phaseCmp = a.getPhase().compareTo(b.getPhase());
                    return phaseCmp != 0 ? phaseCmp
                            : Integer.compare(a.getSortOrder(), b.getSortOrder());
                })
                .map(this::mapExercise)
                .toList();

        MuscleGroup targetMuscle = exerciseResponses.stream()
                .map(WorkoutExerciseResponse::getPrimaryMuscle)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(null);

        return WorkoutResponse.builder()
                .id(workout.getId())
                .date(workout.getDate())
                .totalDurationMinutes(workout.getTotalDurationMinutes())
                .status(workout.getStatus())
                .targetMuscle(targetMuscle)
                .exercises(exerciseResponses)
                .build();
    }

    private WorkoutExerciseResponse mapExercise(WorkoutExercise we) {
        Exercise e = we.getExercise();
        List<WorkoutSetResponse> setResponses = we.getSets().stream()
                .sorted((a, b) -> Integer.compare(a.getSetNumber(), b.getSetNumber()))
                .map(this::mapSet)
                .toList();

        return WorkoutExerciseResponse.builder()
                .id(we.getId())
                .exerciseId(e.getId())
                .exerciseName(e.getName())
                .primaryMuscle(e.getPrimaryMuscle())
                .secondaryMuscles(e.getSecondaryMuscles())
                .exerciseType(e.getExerciseType())
                .phase(we.getPhase())
                .sortOrder(we.getSortOrder())
                .targetSets(we.getTargetSets())
                .targetReps(we.getTargetReps())
                .restSeconds(we.getRestSeconds())
                .notes(we.getNotes())
                .mediaUrl(e.getMediaUrl())
                .sets(setResponses)
                .build();
    }

    private WorkoutSetResponse mapSet(WorkoutSet set) {
        return WorkoutSetResponse.builder()
                .id(set.getId())
                .setNumber(set.getSetNumber())
                .weight(set.getWeight())
                .reps(set.getReps())
                .distance(set.getDistance())
                .durationSeconds(set.getDurationSeconds())
                .notes(set.getNotes())
                .build();
    }
}
