package com.harish.flexfit.dto.response;

import java.util.UUID;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EquipmentResponse {
    private UUID id;
    private String name;
}
