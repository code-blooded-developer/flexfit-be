package com.harish.flexfit.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.harish.flexfit.dto.request.LoginRequest;
import com.harish.flexfit.dto.response.AuthResponse;
import com.harish.flexfit.entity.User;
import com.harish.flexfit.exception.BadRequestException;
import com.harish.flexfit.repo.UserRepository;
import com.harish.flexfit.security.JwtUtil;
import com.harish.flexfit.service.AuthService;

@Service
public class AuthServiceImpl implements AuthService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    private static final Logger logger = LoggerFactory.getLogger(AuthServiceImpl.class);

    @Override
    public AuthResponse login(LoginRequest request) {
        logger.info("Attempting login for email: {}", request.getEmail());

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> {
                    logger.warn("Login failed for email: {} - user not found", request.getEmail());
                    return new BadRequestException("Invalid email or password");
                });

        if(!user.isActive()) {
            logger.warn("Login failed for email: {} - user account is disabled", request.getEmail());
            throw new BadRequestException("User account is disabled");
        }

        if(!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            logger.warn("Login failed for email: {} - invalid password", request.getEmail());
            throw new BadRequestException("Invalid email or password");
        }

        logger.info("Login successful for email: {}", request.getEmail());

        String token = jwtUtil.generateToken(user.getEmail(), user.getRole().name());
        return new AuthResponse(user.getEmail(), token, user.getRole().name());
    }
}
