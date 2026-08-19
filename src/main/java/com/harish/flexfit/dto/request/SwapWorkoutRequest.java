package com.harish.flexfit.dto.request;

import com.harish.flexfit.enums.MuscleGroup;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SwapWorkoutRequest {
    @NotNull(message = "Muscle is required")
    private MuscleGroup muscle;
}
