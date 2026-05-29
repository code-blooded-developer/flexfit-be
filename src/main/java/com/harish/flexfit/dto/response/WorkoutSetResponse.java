package com.harish.flexfit.dto.response;

import java.util.UUID;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class WorkoutSetResponse {
    private UUID id;
    private Integer setNumber;
    private Double weight;
    private Integer reps;
    private Double distance;
    private Integer durationSeconds;
    private String notes;
}
