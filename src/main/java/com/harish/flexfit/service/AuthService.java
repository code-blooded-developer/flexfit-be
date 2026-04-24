package com.harish.flexfit.service;

import com.harish.flexfit.dto.request.LoginRequest;
import com.harish.flexfit.dto.request.RefreshTokenRequest;
import com.harish.flexfit.dto.response.AuthResponse;
import com.harish.flexfit.entity.User;

public interface AuthService {
    AuthResponse login(LoginRequest request);
    AuthResponse refreshToken(RefreshTokenRequest request);
    AuthResponse generateTokensForUser(User user);
}
