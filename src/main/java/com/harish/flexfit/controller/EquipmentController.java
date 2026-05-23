package com.harish.flexfit.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.harish.flexfit.dto.request.EquipmentRequest;
import com.harish.flexfit.dto.response.ApiResponse;
import com.harish.flexfit.dto.response.EquipmentResponse;
import com.harish.flexfit.dto.response.EquipmentSelectionResponse;
import com.harish.flexfit.entity.Equipment;
import com.harish.flexfit.enums.WorkoutEnvironment;
import com.harish.flexfit.service.EquipmentService;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/v1/equipments")
@Slf4j
public class EquipmentController {

    @Autowired
    private EquipmentService equipmentService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<EquipmentResponse>>> getEquipments() {
        log.info("Fetching all equipment");

        List<EquipmentResponse> equipment = equipmentService.getEquipments();

        return ResponseEntity.ok(ApiResponse.success(equipment));
    }

    @GetMapping(params = "environment")
    public ResponseEntity<ApiResponse<List<EquipmentSelectionResponse>>> getEquipmentsByEnvironment(
            @RequestParam WorkoutEnvironment environment) {
        log.info("Fetching equipment for environment: {}", environment);

        List<EquipmentSelectionResponse> equipment = equipmentService.getEquipmentsByEnvironment(environment);

        return ResponseEntity.ok(ApiResponse.success(equipment));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<EquipmentResponse>> getEquipmentById(@PathVariable UUID id) {
        log.info("Fetching equipment with id={}", id);

        EquipmentResponse equipment = equipmentService.getEquipmentById(id);

        return ResponseEntity.ok(ApiResponse.success(equipment));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Equipment>> createEquipment(
            @Valid @RequestBody EquipmentRequest request) {
        log.info("Creating equipment: name={}", request.getName());

        Equipment equipment = equipmentService.createEquipment(request);

        return ResponseEntity.ok(ApiResponse.success("Equipment created successfully", equipment));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Equipment>> updateEquipment(
            @PathVariable UUID id,
            @Valid @RequestBody EquipmentRequest request) {
        log.info("Updating equipment with id={}", id);

        Equipment equipment = equipmentService.updateEquipment(id, request);

        return ResponseEntity.ok(ApiResponse.success("Equipment updated successfully", equipment));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteEquipment(@PathVariable UUID id) {
        log.info("Deleting equipment with id={}", id);

        equipmentService.deleteEquipment(id);

        return ResponseEntity.ok(ApiResponse.success("Equipment deleted successfully", null));
    }
}
