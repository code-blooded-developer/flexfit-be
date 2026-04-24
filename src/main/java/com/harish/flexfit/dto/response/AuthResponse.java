package com.harish.flexfit.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthResponse {
    private String email;
    private String accessToken;
    private String refreshToken;
    private String role;
}
