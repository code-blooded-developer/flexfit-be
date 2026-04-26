package com.harish.flexfit.service;

import com.harish.flexfit.dto.request.LoginRequest;
import com.harish.flexfit.dto.response.AuthResponse;
import com.harish.flexfit.entity.User;

public interface AuthService {
    AuthResponse login(LoginRequest request);
    AuthResponse refreshToken(String refreshToken);
    AuthResponse generateTokensForUser(User user);
}
