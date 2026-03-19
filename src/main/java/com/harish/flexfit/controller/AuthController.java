package com.harish.flexfit.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @Autowired
    private UserService userService;

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @PostMapping("/signup")
    public ResponseEntity<ApiResponse<UserResponse>> signUp(@Valid @RequestBody SignupRequest request) {
        logger.info("Signup request received for email: {}", request.getEmail());

        User user = userService.registerUser(request);
        UserResponse userResponse = new UserResponse(user.getId(), user.getEmail(), user.getRole().name());

        ApiResponse<UserResponse> response =
                new ApiResponse<>(201, "User registered successfully", userResponse);
        
        logger.info("User registered successfully with email: {}", request.getEmail());
        
        return ResponseEntity.status(201).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthResponse>> login(@Valid @RequestBody LoginRequest request) {
        logger.info("Login request received for email: {}", request.getEmail());

        AuthResponse authResponse = authService.login(request);

        ApiResponse<AuthResponse> response =
                new ApiResponse<>(200, "Login successful", authResponse);
        
        logger.info("Login successful for email: {}", request.getEmail());

        return ResponseEntity.ok(response);
    }
}
