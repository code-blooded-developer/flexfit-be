package com.harish.flexfit.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.harish.flexfit.dto.request.UserProfileRequest;
import com.harish.flexfit.dto.response.ApiResponse;
import com.harish.flexfit.dto.response.UserProfileResponse;
import com.harish.flexfit.service.UserProfileService;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/v1/profiles")
@Slf4j
public class UserProfileController {
    @Autowired
    private UserProfileService userProfileService;

    @PostMapping
    public ResponseEntity<ApiResponse<UserProfileResponse>> createOrUpdate(@Valid @RequestBody UserProfileRequest request) {
        log.info("Received profile save request for userId={}", SecurityContextHolder.getContext().getAuthentication().getPrincipal());

        UUID userId = (UUID) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        UserProfileResponse profile = userProfileService.createOrUpdateProfile(userId, request);

        log.info("Profile save successful for userId={}", userId);

        return ResponseEntity.ok(
            ApiResponse.success("Profile saved successfully", profile));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<UserProfileResponse>> getProfile() {
        log.info("Received profile fetch request for userId={}", SecurityContextHolder.getContext().getAuthentication().getPrincipal());

        UUID userId = (UUID) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        UserProfileResponse profile = userProfileService.getProfile(userId);

        log.info("Profile retrieved successfully for userId={}", userId);

        return ResponseEntity.ok(
            ApiResponse.success("Profile fetched successfully", profile)
        );
    }
    
}
