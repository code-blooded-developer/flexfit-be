package com.harish.flexfit.dto.request;

import java.util.List;
import java.util.UUID;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AddExercisesRequest {
    @NotEmpty
    private List<@NotNull UUID> exerciseIds;
}
