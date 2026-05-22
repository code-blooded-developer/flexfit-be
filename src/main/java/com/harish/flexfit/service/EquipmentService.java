package com.harish.flexfit.service;

import java.util.List;
import java.util.UUID;

import com.harish.flexfit.dto.response.EquipmentSelectionResponse;
import com.harish.flexfit.enums.WorkoutEnvironment;

public interface EquipmentService {
    List<EquipmentSelectionResponse> getEquipmentForEnvironment(WorkoutEnvironment environment, UUID userId);
}
