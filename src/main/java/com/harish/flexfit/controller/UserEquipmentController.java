package com.harish.flexfit.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.harish.flexfit.dto.request.EquipmentSelectionRequest;
import com.harish.flexfit.dto.response.ApiResponse;
import com.harish.flexfit.dto.response.UserEquipmentResponse;
import com.harish.flexfit.service.EquipmentService;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/v1/user-equipments")
@Slf4j
public class UserEquipmentController {

    @Autowired
    private EquipmentService equipmentService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<UserEquipmentResponse>>> getUserEquipments() {
        UUID userId = (UUID) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        log.info("Fetching user equipment for userId={}", userId);

        List<UserEquipmentResponse> equipment = equipmentService.getUserEquipments(userId);

        return ResponseEntity.ok(ApiResponse.success(equipment));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Void>> saveUserEquipments(
            @Valid @RequestBody EquipmentSelectionRequest request) {
        UUID userId = (UUID) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        log.info("Saving equipment selections for userId={}", userId);

        equipmentService.saveUserEquipments(userId, request);

        return ResponseEntity.ok(ApiResponse.success("Equipment selections saved successfully", null));
    }

    @PutMapping
    public ResponseEntity<ApiResponse<Void>> updateUserEquipments(
            @Valid @RequestBody EquipmentSelectionRequest request) {
        UUID userId = (UUID) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        log.info("Updating equipment selections for userId={}", userId);

        equipmentService.updateUserEquipments(userId, request);

        return ResponseEntity.ok(ApiResponse.success("Equipment selections updated successfully", null));
    }

    @DeleteMapping
    public ResponseEntity<ApiResponse<Void>> deleteUserEquipments() {
        UUID userId = (UUID) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        log.info("Deleting user equipment for userId={}", userId);

        equipmentService.deleteUserEquipments(userId);

        return ResponseEntity.ok(ApiResponse.success("Equipment selections deleted successfully", null));
    }
}
