package com.harish.flexfit.service.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.harish.flexfit.dto.request.LogSetRequest;
import com.harish.flexfit.dto.request.SwapWorkoutRequest;
import com.harish.flexfit.dto.response.AlternateMuscleOptionResponse;
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
import com.harish.flexfit.enums.MeasurementType;
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
    @Transactional(readOnly = true)
    public List<AlternateMuscleOptionResponse> getAlternateMuscles(UUID userId, MuscleGroup currentMuscle) {
        log.info("Getting alternate muscles for userId={}, currentMuscle={}", userId, currentMuscle);
        UserProfile profile = getProfile(userId);
        WorkoutSplit split = profile.getWorkoutSplit();
        List<AlternateMuscleOptionResponse> options = getSplitOptions(split);

        MuscleGroup effectiveCurrentMuscle = currentMuscle;
        if (effectiveCurrentMuscle == null) {
            LocalDate today = LocalDate.now();
            Workout todayWorkout = workoutRepository.findByUserProfile_IdAndDate(profile.getId(), today).orElse(null);
            if (todayWorkout != null) {
                effectiveCurrentMuscle = getWorkoutTargetMuscle(todayWorkout);
            } else {
                List<MuscleGroup> defaultTargetMuscles = determineTargetMuscles(profile);
                effectiveCurrentMuscle = defaultTargetMuscles.isEmpty() ? null : defaultTargetMuscles.get(0);
            }
        }

        final MuscleGroup finalCurrentMuscle = effectiveCurrentMuscle;
        return options.stream()
                .filter(opt -> finalCurrentMuscle == null
                        || (opt.getMuscle() != finalCurrentMuscle && !opt.getTargetMuscles().contains(finalCurrentMuscle)))
                .toList();
    }

    @Override
    @Transactional
    public WorkoutResponse swapTodayWorkout(UUID userId, SwapWorkoutRequest request) {
        log.info("Swapping today's workout for userId={} with muscle={}", userId, request.getMuscle());
        UserProfile profile = getProfile(userId);
        LocalDate today = LocalDate.now();

        Workout workout = workoutRepository
                .findByUserProfile_IdAndDate(profile.getId(), today)
                .orElseGet(() -> {
                    Workout w = new Workout();
                    w.setUserProfile(profile);
                    w.setDate(today);
                    w.setTotalDurationMinutes(profile.getWorkoutDurationMinutes());
                    w.setStatus(WorkoutStatus.PLANNED);
                    return w;
                });

        if (workout.getStatus() == WorkoutStatus.COMPLETED) {
            throw new BadRequestException("Cannot swap a completed workout");
        }

        List<AlternateMuscleOptionResponse> options = getSplitOptions(profile.getWorkoutSplit());
        List<MuscleGroup> targetMuscles = options.stream()
                .filter(opt -> opt.getMuscle() == request.getMuscle() || opt.getTargetMuscles().contains(request.getMuscle()))
                .map(AlternateMuscleOptionResponse::getTargetMuscles)
                .findFirst()
                .orElse(List.of(request.getMuscle()));

        Workout updated = populateWorkoutExercises(workout, profile, targetMuscles);
        log.info("Workout swapped successfully for userId={}", userId);
        return map(updated);
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
    public void deleteWorkout(UUID userId, UUID workoutId) {
        log.info("Deleting workout id={} for userId={}", workoutId, userId);
        Workout workout = getOwnedWorkout(userId, workoutId);
        workoutRepository.delete(workout);
    }

    @Override
    @Transactional
    public WorkoutResponse addExercise(UUID userId, UUID workoutId, List<UUID> exerciseIds) {
        log.info("Adding {} exercises to workout id={}", exerciseIds.size(), workoutId);
        Workout workout = getOwnedWorkout(userId, workoutId);
        UserProfile profile = workout.getUserProfile();

        Map<ExerciseCategory, Integer> maxOrders = new java.util.HashMap<>();
        for (ExerciseCategory phase : ExerciseCategory.values()) {
            maxOrders.put(phase, workout.getExercises().stream()
                    .filter(e -> e.getPhase() == phase)
                    .mapToInt(WorkoutExercise::getSortOrder)
                    .max()
                    .orElse(0));
        }

        for (UUID exerciseId : exerciseIds) {
            Exercise exercise = exerciseRepository.findById(exerciseId)
                    .orElseThrow(() -> new ResourceNotFoundException("Exercise not found: " + exerciseId));

            validateEquipmentCompatibility(exercise, profile);
            validateNotUsedRecently(profile.getId(), exercise.getId());

            if (isExerciseInWorkout(workout, exercise.getId())) {
                throw new BadRequestException("Exercise already exists in this workout: " + exercise.getName());
            }

            ExerciseCategory phase = exercise.getCategory();
            int sortOrder = maxOrders.merge(phase, 1, (a, b) -> a + b);

            WorkoutExercise we = new WorkoutExercise();
            we.setWorkout(workout);
            we.setExercise(exercise);
            we.setPhase(phase);
            we.setSortOrder(sortOrder);
            we.setTargetSets(getDefaultSets(profile.getFitnessGoal()));
            we.setRestSeconds(getDefaultRest(profile.getFitnessGoal()));

            if (exercise.getMeasurementType() == MeasurementType.DURATION) {
                we.setTargetDurationSeconds(30);
            } else {
                we.setTargetReps(getDefaultReps(profile.getFitnessGoal()));
            }

            workout.getExercises().add(we);
        }

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
            UUID userId, UUID workoutId,
            ExerciseCategory category, MuscleGroup muscle) {
        log.info("Getting alternatives for workout id={}, category={}, muscle={}",
                workoutId, category, muscle);
        Workout workout = getOwnedWorkout(userId, workoutId);

        UserProfile profile = workout.getUserProfile();
        MuscleGroup targetMuscle = muscle != null ? muscle : getWorkoutTargetMuscle(workout);

        Set<UUID> recentExerciseIds = getRecentExerciseIds(profile.getId());
        Set<UUID> currentExerciseIds = workout.getExercises().stream()
                .map(e -> e.getExercise().getId())
                .collect(Collectors.toSet());

        List<Exercise> candidates = findSuitableExercises(category, null, profile);

        return candidates.stream()
                .filter(e -> !recentExerciseIds.contains(e.getId()))
                .filter(e -> !currentExerciseIds.contains(e.getId()))
                .sorted(Comparator.comparingInt((Exercise e) -> getMuscleMatchRank(e, targetMuscle))
                        .thenComparing(Exercise::getName, String.CASE_INSENSITIVE_ORDER))
                .map(e -> WorkoutExerciseResponse.builder()
                        .exerciseId(e.getId())
                        .exerciseName(e.getName())
                        .primaryMuscle(e.getPrimaryMuscle())
                        .secondaryMuscles(e.getSecondaryMuscles())
                        .exerciseType(e.getExerciseType())
                        .measurementType(e.getMeasurementType())
                        .phase(e.getCategory())
                        .thumbnailKey(e.getThumbnailKey())
                        .videoKey(e.getVideoKey())
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
        int totalMin = profile.getWorkoutDurationMinutes();

        Workout workout = new Workout();
        workout.setUserProfile(profile);
        workout.setDate(LocalDate.now());
        workout.setTotalDurationMinutes(totalMin);
        workout.setStatus(WorkoutStatus.PLANNED);

        return populateWorkoutExercises(workout, profile, targetMuscles);
    }

    private Workout populateWorkoutExercises(Workout workout, UserProfile profile, List<MuscleGroup> targetMuscles) {
        Set<UUID> recentExerciseIds = getRecentExerciseIds(profile.getId());
        int totalMin = workout.getTotalDurationMinutes() != null ? workout.getTotalDurationMinutes() : profile.getWorkoutDurationMinutes();

        List<WorkoutExercise> exercises = new ArrayList<>();
        int order = 0;

        List<Exercise> warmupPool = findSuitableExercises(
                ExerciseCategory.WARMUP, null, profile);
        warmupPool.removeIf(e -> recentExerciseIds.contains(e.getId()));
        List<Exercise> cardioPool = warmupPool.stream()
                .filter(e -> e.getExerciseType() == ExerciseType.CARDIO)
                .toList();
        List<Exercise> regularWarmupPool = warmupPool.stream()
                .filter(e -> e.getExerciseType() != ExerciseType.CARDIO)
                .toList();
        List<Exercise> stretchPool = findSuitableExercises(
                ExerciseCategory.STRETCHING, null, profile);
        stretchPool.removeIf(e -> recentExerciseIds.contains(e.getId()));

        // Cardio warmup first
        int cardioDuration = getDefaultCardioDuration(profile.getFitnessGoal());
        for (Exercise e : pickRandom(cardioPool, 1)) {
            WorkoutExercise we = createExerciseEntry(workout, e,
                    ExerciseCategory.WARMUP, order++, 1, null, 0, cardioDuration);
            exercises.add(we);
        }

        // Regular warmup
        for (Exercise e : pickTargetedExercises(regularWarmupPool, targetMuscles, WARMUP_EXERCISE_COUNT)) {
            Integer warmupDuration = e.getMeasurementType() == MeasurementType.DURATION ? 30 : null;
            exercises.add(createExerciseEntry(workout, e,
                    ExerciseCategory.WARMUP, order++, 1, "10-15", 0, warmupDuration));
        }

        Map<MuscleGroup, Integer> exercisesPerMuscle =
                calculateMainExercisesPerMuscle(targetMuscles, totalMin, profile.getFitnessGoal());

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

            int count = exercisesPerMuscle.getOrDefault(muscle, 1);
            int compoundCount = Math.min((count + 1) / 2, compounds.size());
            int isolationCount = Math.min(count - compoundCount, isolations.size());

            List<Exercise> selected = new ArrayList<>();
            selected.addAll(pickRandom(compounds, compoundCount));
            selected.addAll(pickRandom(isolations, isolationCount));
            selected = selected.stream().distinct().collect(Collectors.toList());

            int sets = getDefaultSets(profile.getFitnessGoal());
            String reps = getDefaultReps(profile.getFitnessGoal());
            int rest = getDefaultRest(profile.getFitnessGoal());

            for (Exercise e : selected) {
                if (!recentExerciseIds.contains(e.getId())) {
                    exercises.add(createExerciseEntry(workout, e,
                            ExerciseCategory.MAIN, order++, sets, reps, rest, null));
                }
            }
        }

        List<Exercise> selectedStretch = pickTargetedExercises(stretchPool, targetMuscles, STRETCH_EXERCISE_COUNT);
        for (Exercise e : selectedStretch) {
            exercises.add(createExerciseEntry(workout, e,
                    ExerciseCategory.STRETCHING, order++, 1, null, 0, 25));
        }

        if (workout.getExercises() == null) {
            workout.setExercises(new ArrayList<>());
        } else {
            workout.getExercises().clear();
        }
        workout.getExercises().addAll(exercises);
        return workoutRepository.save(workout);
    }

    private List<AlternateMuscleOptionResponse> getSplitOptions(WorkoutSplit split) {
        if (split == null) split = WorkoutSplit.FULL_BODY;
        return switch (split) {
            case PUSH_PULL_LEGS -> List.of(
                    AlternateMuscleOptionResponse.builder()
                            .splitDayIndex(0)
                            .name("Push")
                            .muscle(MuscleGroup.CHEST)
                            .targetMuscles(List.of(MuscleGroup.CHEST, MuscleGroup.SHOULDERS, MuscleGroup.TRICEPS))
                            .build(),
                    AlternateMuscleOptionResponse.builder()
                            .splitDayIndex(1)
                            .name("Pull")
                            .muscle(MuscleGroup.BACK)
                            .targetMuscles(List.of(MuscleGroup.BACK, MuscleGroup.BICEPS, MuscleGroup.HAMSTRINGS))
                            .build(),
                    AlternateMuscleOptionResponse.builder()
                            .splitDayIndex(2)
                            .name("Legs & Core")
                            .muscle(MuscleGroup.LEGS)
                            .targetMuscles(List.of(MuscleGroup.LEGS, MuscleGroup.GLUTES, MuscleGroup.CALVES, MuscleGroup.CORE))
                            .build()
            );
            case UPPER_LOWER -> List.of(
                    AlternateMuscleOptionResponse.builder()
                            .splitDayIndex(0)
                            .name("Upper Body")
                            .muscle(MuscleGroup.CHEST)
                            .targetMuscles(List.of(MuscleGroup.CHEST, MuscleGroup.BACK, MuscleGroup.SHOULDERS, MuscleGroup.BICEPS, MuscleGroup.TRICEPS))
                            .build(),
                    AlternateMuscleOptionResponse.builder()
                            .splitDayIndex(1)
                            .name("Lower Body")
                            .muscle(MuscleGroup.LEGS)
                            .targetMuscles(List.of(MuscleGroup.LEGS, MuscleGroup.GLUTES, MuscleGroup.HAMSTRINGS, MuscleGroup.CALVES, MuscleGroup.CORE))
                            .build()
            );
            case BRO_SPLIT -> List.of(
                    AlternateMuscleOptionResponse.builder()
                            .splitDayIndex(0)
                            .name("Chest")
                            .muscle(MuscleGroup.CHEST)
                            .targetMuscles(List.of(MuscleGroup.CHEST))
                            .build(),
                    AlternateMuscleOptionResponse.builder()
                            .splitDayIndex(1)
                            .name("Back")
                            .muscle(MuscleGroup.BACK)
                            .targetMuscles(List.of(MuscleGroup.BACK))
                            .build(),
                    AlternateMuscleOptionResponse.builder()
                            .splitDayIndex(2)
                            .name("Shoulders")
                            .muscle(MuscleGroup.SHOULDERS)
                            .targetMuscles(List.of(MuscleGroup.SHOULDERS))
                            .build(),
                    AlternateMuscleOptionResponse.builder()
                            .splitDayIndex(3)
                            .name("Legs")
                            .muscle(MuscleGroup.LEGS)
                            .targetMuscles(List.of(MuscleGroup.LEGS))
                            .build(),
                    AlternateMuscleOptionResponse.builder()
                            .splitDayIndex(4)
                            .name("Arms")
                            .muscle(MuscleGroup.BICEPS)
                            .targetMuscles(List.of(MuscleGroup.BICEPS, MuscleGroup.TRICEPS))
                            .build()
            );
            case FULL_BODY -> List.of(
                    AlternateMuscleOptionResponse.builder()
                            .splitDayIndex(0)
                            .name("Full Body")
                            .muscle(MuscleGroup.FULL_BODY)
                            .targetMuscles(List.of(
                                    MuscleGroup.CHEST, MuscleGroup.BACK, MuscleGroup.LEGS,
                                    MuscleGroup.SHOULDERS, MuscleGroup.BICEPS,
                                    MuscleGroup.TRICEPS, MuscleGroup.CORE))
                            .build()
            );
        };
    }

    private List<MuscleGroup> determineTargetMuscles(UserProfile profile) {
        WorkoutSplit split = profile.getWorkoutSplit();
        if (split == null) split = WorkoutSplit.FULL_BODY;

        List<AlternateMuscleOptionResponse> options = getSplitOptions(split);
        long workoutCount = workoutRepository.countByUserProfile_Id(profile.getId());
        int dayIndex = (int) (workoutCount % options.size());

        return options.get(dayIndex).getTargetMuscles();
    }

    private List<Exercise> findSuitableExercises(
            ExerciseCategory category, MuscleGroup muscle, UserProfile profile) {
        Set<UUID> userEquipmentIds = getUserEquipmentIds(profile);
        Set<DifficultyLevel> allowedDifficulties = getAllowedDifficulties(profile.getExperienceLevel());

        return exerciseRepository.findAll().stream()
                .filter(e -> category == null || e.getCategory() == category)
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
            int order, int sets, String reps, int rest, Integer duration) {
        WorkoutExercise we = new WorkoutExercise();
        we.setWorkout(workout);
        we.setExercise(exercise);
        we.setPhase(phase);
        we.setSortOrder(order);
        we.setTargetSets(sets);
        we.setRestSeconds(rest);

        MeasurementType mt = exercise.getMeasurementType();
        if (mt == MeasurementType.DURATION && duration != null) {
            we.setTargetDurationSeconds(duration);
            we.setTargetReps(null);
        } else {
            we.setTargetReps(reps);
            we.setTargetDurationSeconds(null);
        }

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

    private int getDefaultCardioDuration(FitnessGoal goal) {
        if (goal == null) return 600;
        return switch (goal) {
            case LOSE_WEIGHT -> 1200;
            default -> 600;
        };
    }

    private int estimateTimePerExercise(FitnessGoal goal) {
        int sets = getDefaultSets(goal);
        int rest = getDefaultRest(goal);
        return (sets * 40) + ((sets - 1) * rest);
    }

    private int estimateWarmupDuration(FitnessGoal goal) {
        int cardioDuration = getDefaultCardioDuration(goal);
        int regularWarmupDuration = WARMUP_EXERCISE_COUNT * 120;
        return cardioDuration + regularWarmupDuration;
    }

    private Map<MuscleGroup, Integer> calculateMainExercisesPerMuscle(
            List<MuscleGroup> targetMuscles, int totalDurationMinutes, FitnessGoal goal) {
        int warmupSeconds = estimateWarmupDuration(goal);
        int stretchSeconds = STRETCH_EXERCISE_COUNT * 120;
        int mainBudgetSeconds = (totalDurationMinutes * 60) - warmupSeconds - stretchSeconds;

        int timePerExercise = estimateTimePerExercise(goal);
        int totalExercises = mainBudgetSeconds > 0
                ? mainBudgetSeconds / timePerExercise
                : 1;

        int muscleCount = targetMuscles.size();
        int exercisesPerMuscle = Math.max(1, totalExercises / muscleCount);
        exercisesPerMuscle = Math.min(exercisesPerMuscle, 4);

        Map<MuscleGroup, Integer> result = new HashMap<>();
        for (MuscleGroup muscle : targetMuscles) {
            result.put(muscle, exercisesPerMuscle);
        }

        int remaining = totalExercises - (exercisesPerMuscle * muscleCount);
        for (MuscleGroup muscle : targetMuscles) {
            if (remaining <= 0) break;
            result.merge(muscle, 1, Integer::sum);
            remaining--;
        }

        return result;
    }

    private List<Exercise> pickRandom(List<Exercise> exercises, int count) {
        if (exercises.isEmpty()) return List.of();
        List<Exercise> shuffled = new ArrayList<>(exercises);
        Collections.shuffle(shuffled);
        return shuffled.subList(0, Math.min(count, shuffled.size()));
    }

    private List<Exercise> pickTargetedExercises(
            List<Exercise> pool, List<MuscleGroup> targetMuscles, int count) {
        Set<MuscleGroup> targetSet = Set.copyOf(targetMuscles);
        List<Exercise> result = new ArrayList<>();
        Set<UUID> selectedIds = new java.util.HashSet<>();

        List<Exercise> primaryMatch = pool.stream()
                .filter(e -> targetSet.contains(e.getPrimaryMuscle()))
                .collect(Collectors.toList());
        for (Exercise e : pickRandom(primaryMatch, count)) {
            result.add(e);
            selectedIds.add(e.getId());
        }

        if (result.size() < count) {
            List<Exercise> secondaryMatch = pool.stream()
                    .filter(e -> !selectedIds.contains(e.getId()))
                    .filter(e -> e.getSecondaryMuscles() != null
                            && e.getSecondaryMuscles().stream().anyMatch(targetSet::contains))
                    .collect(Collectors.toList());
            for (Exercise e : pickRandom(secondaryMatch, count - result.size())) {
                result.add(e);
                selectedIds.add(e.getId());
            }
        }

        if (result.size() < count) {
            List<Exercise> fullBody = pool.stream()
                    .filter(e -> !selectedIds.contains(e.getId()))
                    .filter(e -> e.getPrimaryMuscle() == MuscleGroup.FULL_BODY)
                    .collect(Collectors.toList());
            for (Exercise e : pickRandom(fullBody, count - result.size())) {
                result.add(e);
                selectedIds.add(e.getId());
            }
        }

        if (result.size() < count) {
            List<Exercise> remaining = pool.stream()
                    .filter(e -> !selectedIds.contains(e.getId()))
                    .collect(Collectors.toList());
            result.addAll(pickRandom(remaining, count - result.size()));
        }

        return result;
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

    private int getMuscleMatchRank(Exercise e, MuscleGroup targetMuscle) {
        if (targetMuscle == null) {
            return 2;
        }
        if (e.getPrimaryMuscle() == targetMuscle) {
            return 0;
        }
        if (e.getSecondaryMuscles() != null && e.getSecondaryMuscles().contains(targetMuscle)) {
            return 1;
        }
        return 2;
    }

    private MuscleGroup getWorkoutTargetMuscle(Workout workout) {
        if (workout.getExercises() != null && !workout.getExercises().isEmpty()) {
            MuscleGroup target = workout.getExercises().stream()
                    .map(we -> we.getExercise().getPrimaryMuscle())
                    .filter(Objects::nonNull)
                    .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                    .entrySet().stream()
                    .max(Map.Entry.comparingByValue())
                    .map(Map.Entry::getKey)
                    .orElse(null);
            if (target != null) {
                return target;
            }
        }
        List<MuscleGroup> splitTargetMuscles = determineTargetMuscles(workout.getUserProfile());
        return splitTargetMuscles.isEmpty() ? null : splitTargetMuscles.get(0);
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

        MuscleGroup targetMuscle = getWorkoutTargetMuscle(workout);

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
                .measurementType(e.getMeasurementType())
                .phase(we.getPhase())
                .sortOrder(we.getSortOrder())
                .targetSets(we.getTargetSets())
                .targetReps(we.getTargetReps())
                .restSeconds(we.getRestSeconds())
                .targetDurationSeconds(we.getTargetDurationSeconds())
                .notes(we.getNotes())
                .thumbnailKey(e.getThumbnailKey())
                .videoKey(e.getVideoKey())
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
