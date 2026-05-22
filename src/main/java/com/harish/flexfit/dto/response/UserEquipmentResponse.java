package com.harish.flexfit.dto.response;

import java.util.List;
import java.util.UUID;

import com.harish.flexfit.enums.EquipmentCategory;
import com.harish.flexfit.enums.EquipmentType;
import com.harish.flexfit.enums.WeightUnit;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserEquipmentResponse {
    private UUID id;
    private String name;
    private EquipmentType type;
    private EquipmentCategory category;
    private boolean hasVariants;
    private List<SelectedVariant> variants;

    @Data
    @Builder
    public static class SelectedVariant {
        private UUID variantId;
        private Double weight;
        private WeightUnit weightUnit;
        private int quantity;
    }
}
