package com.harish.flexfit.dto.request;

import java.util.UUID;

import com.harish.flexfit.enums.ExerciseCategory;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AddExerciseRequest {
    @NotNull
    private UUID exerciseId;

    @NotNull
    private ExerciseCategory phase;

    private Integer targetSets;
    private String targetReps;
    private Integer restSeconds;
    private String notes;
}
