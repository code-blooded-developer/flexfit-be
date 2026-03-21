package com.harish.flexfit.dto.response;

import java.util.List;
import java.util.UUID;

import com.harish.flexfit.enums.DifficultyLevel;
import com.harish.flexfit.enums.ExerciseCategory;
import com.harish.flexfit.enums.ExerciseType;
import com.harish.flexfit.enums.MediaType;
import com.harish.flexfit.enums.MuscleGroup;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ExerciseResponse {
    private UUID id;
    private String name;

    private MuscleGroup primaryMuscle;
    private List<MuscleGroup> secondaryMuscles;

    private DifficultyLevel difficultyLevel;
    private ExerciseType exerciseType;
    private ExerciseCategory category;

    private List<String> equipmentNames;

    private String mediaUrl;
    private MediaType mediaType;
}
