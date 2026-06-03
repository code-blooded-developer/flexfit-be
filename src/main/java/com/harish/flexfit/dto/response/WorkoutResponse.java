package com.harish.flexfit.dto.response;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import com.harish.flexfit.enums.MuscleGroup;
import com.harish.flexfit.enums.WorkoutStatus;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class WorkoutResponse {
    private UUID id;
    private LocalDate date;
    private Integer totalDurationMinutes;
    private WorkoutStatus status;
    private MuscleGroup targetMuscle;
    private List<WorkoutExerciseResponse> exercises;
}
