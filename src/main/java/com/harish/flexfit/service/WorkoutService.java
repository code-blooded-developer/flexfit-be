package com.harish.flexfit.service;

import java.util.List;
import java.util.UUID;

import com.harish.flexfit.dto.request.AddExerciseRequest;
import com.harish.flexfit.dto.request.LogSetRequest;
import com.harish.flexfit.dto.response.ExerciseAlternativeResponse;
import com.harish.flexfit.dto.response.WorkoutResponse;
import com.harish.flexfit.dto.response.WorkoutSetResponse;

public interface WorkoutService {
    WorkoutResponse getTodayWorkout(UUID userId);

    WorkoutResponse getWorkoutById(UUID userId, UUID workoutId);

    List<WorkoutResponse> getWorkoutHistory(UUID userId);

    WorkoutResponse completeWorkout(UUID userId, UUID workoutId);

    WorkoutResponse skipWorkout(UUID userId, UUID workoutId);

    WorkoutResponse addExercise(UUID userId, UUID workoutId, AddExerciseRequest request);

    WorkoutResponse removeExercise(UUID userId, UUID workoutId, UUID exerciseId);

    List<ExerciseAlternativeResponse> getAlternativeExercises(UUID userId, UUID workoutId, UUID exerciseId);

    WorkoutResponse replaceExercise(UUID userId, UUID workoutId, UUID exerciseId, UUID alternativeExerciseId);

    WorkoutSetResponse logSet(UUID userId, UUID workoutId, UUID exerciseId, LogSetRequest request);

    WorkoutSetResponse updateSet(UUID userId, UUID workoutId, UUID exerciseId, UUID setId, LogSetRequest request);

    void deleteSet(UUID userId, UUID workoutId, UUID exerciseId, UUID setId);
}
