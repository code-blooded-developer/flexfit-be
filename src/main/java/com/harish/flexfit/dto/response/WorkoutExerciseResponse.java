package com.harish.flexfit.dto.response;

import java.util.List;
import java.util.UUID;

import com.harish.flexfit.enums.ExerciseCategory;
import com.harish.flexfit.enums.ExerciseType;
import com.harish.flexfit.enums.MeasurementType;
import com.harish.flexfit.enums.MuscleGroup;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class WorkoutExerciseResponse {
    private UUID id;
    private UUID exerciseId;
    private String exerciseName;
    private MuscleGroup primaryMuscle;
    private List<MuscleGroup> secondaryMuscles;
    private ExerciseType exerciseType;
    private MeasurementType measurementType;
    private ExerciseCategory phase;
    private Integer sortOrder;
    private Integer targetSets;
    private String targetReps;
    private Integer restSeconds;
    private Integer targetDurationSeconds;
    private String notes;
    private String thumbnailKey;
    private String videoKey;
    private List<WorkoutSetResponse> sets;
}
