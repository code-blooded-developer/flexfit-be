package com.harish.flexfit.dto.request;

import com.harish.flexfit.enums.EquipmentCategory;
import com.harish.flexfit.enums.EquipmentType;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class EquipmentRequest {
    @NotBlank(message = "Name is required")
    private String name;

    @NotNull(message = "Type is required")
    private EquipmentType type;

    @NotNull(message = "Category is required")
    private EquipmentCategory category;

    private boolean hasVariants;
}
