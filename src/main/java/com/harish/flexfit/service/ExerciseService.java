package com.harish.flexfit.service;

import java.util.List;
import java.util.UUID;

import com.harish.flexfit.dto.response.ExerciseResponse;
import com.harish.flexfit.enums.DifficultyLevel;
import com.harish.flexfit.enums.ExerciseCategory;
import com.harish.flexfit.enums.MuscleGroup;

public interface ExerciseService {
    List<ExerciseResponse> getAllExercises();

    ExerciseResponse getExerciseById(UUID id);

    List<ExerciseResponse> filterExercises(
        MuscleGroup muscle,
        DifficultyLevel difficulty,
        List<UUID> equipmentIds,
        ExerciseCategory category
    );
}
