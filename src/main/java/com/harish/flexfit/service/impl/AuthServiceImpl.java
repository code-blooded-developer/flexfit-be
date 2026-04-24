package com.harish.flexfit.service.impl;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.harish.flexfit.dto.request.LoginRequest;
import com.harish.flexfit.dto.request.RefreshTokenRequest;
import com.harish.flexfit.dto.response.AuthResponse;
import com.harish.flexfit.entity.User;
import com.harish.flexfit.exception.BadRequestException;
import com.harish.flexfit.repo.UserRepository;
import com.harish.flexfit.security.JwtUtil;
import com.harish.flexfit.service.AuthService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class AuthServiceImpl implements AuthService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public AuthResponse login(LoginRequest request) {
        log.info("Attempting login for email: {}", request.getEmail());

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> {
                    log.warn("Login failed for email: {} - user not found", request.getEmail());
                    return new BadRequestException("Invalid email or password");
                });

        if(!user.isActive()) {
            log.warn("Login failed for email: {} - user account is disabled", request.getEmail());
            throw new BadRequestException("User account is disabled");
        }

        if(!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            log.warn("Login failed for email: {} - invalid password", request.getEmail());
            throw new BadRequestException("Invalid email or password");
        }

        log.info("Login successful for email: {}", request.getEmail());

        String accessToken = jwtUtil.generateAccessToken(user);
        String refreshToken = jwtUtil.generateRefreshToken(user);
        
        return new AuthResponse(user.getEmail(), accessToken, refreshToken, user.getRole().name());
    }

    @Override
    public AuthResponse refreshToken(RefreshTokenRequest request) {
        log.info("Refresh token request received");

        String refreshToken = request.getRefreshToken();

        if (!jwtUtil.validateToken(refreshToken)) {
            log.warn("Invalid refresh token");
            throw new BadRequestException("Invalid or expired refresh token");
        }

        if (!jwtUtil.isRefreshToken(refreshToken)) {
            log.warn("Token is not a refresh token");
            throw new BadRequestException("Invalid token type");
        }

        UUID userId = jwtUtil.extractUserId(refreshToken);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> {
                    log.warn("User not found for refresh token");
                    return new BadRequestException("User not found");
                });

        if (!user.isActive()) {
            log.warn("User account is disabled");
            throw new BadRequestException("User account is disabled");
        }

        log.info("Refresh token successful for user: {}", user.getEmail());

        String newAccessToken = jwtUtil.generateAccessToken(user);
        String newRefreshToken = jwtUtil.generateRefreshToken(user);

        return new AuthResponse(user.getEmail(), newAccessToken, newRefreshToken, user.getRole().name());
    }

    @Override
    public AuthResponse generateTokensForUser(User user) {
        log.info("Generating tokens for user: {}", user.getEmail());

        String accessToken = jwtUtil.generateAccessToken(user);
        String refreshToken = jwtUtil.generateRefreshToken(user);

        return new AuthResponse(user.getEmail(), accessToken, refreshToken, user.getRole().name());
    }
}
