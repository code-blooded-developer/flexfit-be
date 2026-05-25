package com.harish.flexfit.dto.response;

import java.util.UUID;

import com.harish.flexfit.enums.WeightUnit;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EquipmentVariantResponse {
    private UUID id;
    private Double weight;
    private WeightUnit weightUnit;
    private int quantity;
}
