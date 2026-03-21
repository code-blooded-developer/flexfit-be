package com.harish.flexfit.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.harish.flexfit.dto.request.LoginRequest;
import com.harish.flexfit.dto.request.SignupRequest;
import com.harish.flexfit.dto.response.ApiResponse;
import com.harish.flexfit.dto.response.AuthResponse;
import com.harish.flexfit.dto.response.UserResponse;
import com.harish.flexfit.entity.User;
import com.harish.flexfit.service.AuthService;
import com.harish.flexfit.service.UserService;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/v1/auth")
@Slf4j
public class AuthController {
    @Autowired
    private AuthService authService;

    @Autowired
    private UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<ApiResponse<UserResponse>> signUp(@Valid @RequestBody SignupRequest request) {
        log.info("Signup request received for email: {}", request.getEmail());

        User user = userService.registerUser(request);
        UserResponse userResponse = new UserResponse(user.getId(), user.getEmail(), user.getRole().name());
        
        log.info("User registered successfully with email: {}", request.getEmail());
        
        return ResponseEntity.ok(
            ApiResponse.success("User registered successfully", userResponse)
        );
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthResponse>> login(@Valid @RequestBody LoginRequest request) {
        log.info("Login request received for email: {}", request.getEmail());

        AuthResponse authResponse = authService.login(request);
        
        log.info("Login successful for email: {}", request.getEmail());

        return ResponseEntity.ok(
            ApiResponse.success("Login successful", authResponse)
        );
    }
}
