package com.harish.flexfit.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class LogSetRequest {
    @NotNull
    @Min(1)
    private Integer setNumber;

    private Double weight;
    private Integer reps;
    private Double distance;
    private Integer durationSeconds;
    private String notes;
}
