package com.harish.flexfit.dto.request;

import java.util.List;
import java.util.UUID;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class EquipmentSelectionRequest {
    @Valid
    private List<EquipmentSelection> equipmentSelections;

    @Data
    public static class EquipmentSelection {
        @NotNull
        private UUID equipmentId;
        private List<VariantSelection> variantSelections;
    }

    @Data
    public static class VariantSelection {
        @NotNull
        private UUID variantId;
        @Min(1)
        private int quantity = 1;
    }
}
