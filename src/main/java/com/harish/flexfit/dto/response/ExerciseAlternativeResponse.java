package com.harish.flexfit.dto.response;

import java.util.List;
import java.util.UUID;

import com.harish.flexfit.enums.DifficultyLevel;
import com.harish.flexfit.enums.ExerciseType;
import com.harish.flexfit.enums.MuscleGroup;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ExerciseAlternativeResponse {
    private UUID exerciseId;
    private String exerciseName;
    private MuscleGroup primaryMuscle;
    private List<MuscleGroup> secondaryMuscles;
    private ExerciseType exerciseType;
    private DifficultyLevel difficultyLevel;
}
