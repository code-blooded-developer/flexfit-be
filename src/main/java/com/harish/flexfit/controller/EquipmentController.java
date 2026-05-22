package com.harish.flexfit.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.harish.flexfit.dto.request.EquipmentSelectionRequest;
import com.harish.flexfit.dto.response.ApiResponse;
import com.harish.flexfit.dto.response.EquipmentSelectionResponse;
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
    public ResponseEntity<ApiResponse<List<EquipmentSelectionResponse>>> getEquipment(
            @RequestParam(required = false) WorkoutEnvironment environment) {
        UUID userId = (UUID) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        log.info("Fetching equipment for userId={}, environment={}", userId, environment);

        List<EquipmentSelectionResponse> equipment = equipmentService.getEquipmentForEnvironment(environment, userId);

        return ResponseEntity.ok(ApiResponse.success(equipment));
    }

    @PutMapping("/selections")
    public ResponseEntity<ApiResponse<Void>> saveSelections(
            @Valid @RequestBody EquipmentSelectionRequest request) {
        UUID userId = (UUID) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        log.info("Saving equipment selections for userId={}", userId);

        equipmentService.saveEquipmentSelections(userId, request);

        return ResponseEntity.ok(ApiResponse.success("Equipment selections saved successfully", null));
    }
}
