package com.harish.flexfit.dto.response;

import java.util.List;
import java.util.UUID;

import com.harish.flexfit.enums.EquipmentCategory;
import com.harish.flexfit.enums.EquipmentType;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EquipmentResponse {
    private UUID id;
    private String name;
    private EquipmentType type;
    private EquipmentCategory category;
    private boolean hasVariants;
    private List<EquipmentVariantResponse> variants;
}
