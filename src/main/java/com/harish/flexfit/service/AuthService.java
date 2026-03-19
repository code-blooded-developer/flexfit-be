package com.harish.flexfit.service;

import com.harish.flexfit.dto.request.LoginRequest;
import com.harish.flexfit.dto.response.AuthResponse;

public interface AuthService {
    AuthResponse login(LoginRequest request);
}
