package com.harish.flexfit.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.harish.flexfit.dto.auth.AuthResponse;
import com.harish.flexfit.dto.auth.LoginRequest;
import com.harish.flexfit.dto.auth.SignupRequest;
import com.harish.flexfit.service.AuthService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping("/signup")
    public String signUp(@Valid @RequestBody SignupRequest request) {
        authService.signUp(request);
        return "User signed up successfully";
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody LoginRequest request) {
        String token = authService.login(request);
        return new AuthResponse(token);
    }
}
