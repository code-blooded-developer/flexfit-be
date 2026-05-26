package com.harish.flexfit.service;

import java.util.List;
import java.util.UUID;

import com.harish.flexfit.dto.request.EquipmentRequest;
import com.harish.flexfit.dto.request.EquipmentSelectionRequest;
import com.harish.flexfit.dto.response.EquipmentResponse;
import com.harish.flexfit.dto.response.EquipmentSelectionResponse;
import com.harish.flexfit.entity.Equipment;
import com.harish.flexfit.enums.WorkoutEnvironment;

public interface EquipmentService {
    List<EquipmentResponse> getEquipments();
    List<EquipmentSelectionResponse> getEquipmentsByEnvironment(WorkoutEnvironment environment);
    EquipmentResponse getEquipmentById(UUID id);
    Equipment createEquipment(EquipmentRequest request);
    Equipment updateEquipment(UUID id, EquipmentRequest request);
    void deleteEquipment(UUID id);

    List<EquipmentSelectionResponse> getUserEquipments(UUID userId);
    void saveUserEquipments(UUID userId, EquipmentSelectionRequest request);
    void updateUserEquipments(UUID userId, EquipmentSelectionRequest request);
    void deleteUserEquipments(UUID userId);
}
