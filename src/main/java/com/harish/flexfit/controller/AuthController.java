package com.harish.flexfit.controller;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.harish.flexfit.dto.request.LoginRequest;
import com.harish.flexfit.dto.request.SignupRequest;
import com.harish.flexfit.dto.response.ApiResponse;
import com.harish.flexfit.dto.response.AuthResponse;
import com.harish.flexfit.dto.response.UserResponse;
import com.harish.flexfit.entity.User;
import com.harish.flexfit.exception.BadRequestException;
import com.harish.flexfit.service.AuthService;
import com.harish.flexfit.service.UserService;

import jakarta.servlet.http.Cookie;
import org.springframework.http.ResponseCookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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

    private String accessTokenCookieName = "ACCESS_TOKEN";

    private String refreshTokenCookieName = "REFRESH_TOKEN";

    @Value("${jwt.access-token-expiration:1800000}")
    private long accessTokenExpiration;

    @Value("${jwt.refresh-token-expiration:604800000}")
    private long refreshTokenExpiration;

    @PostMapping("/signup")
    public ResponseEntity<ApiResponse<UserResponse>> signUp(
            @Valid @RequestBody SignupRequest request,
            HttpServletResponse response) {
        log.info("Signup request received for email: {}", request.getEmail());

        User user = userService.registerUser(request);
        UserResponse userResponse = new UserResponse(
            user.getId(), 
            user.getEmail(), 
            user.getRole().name(),
            user.isProfileComplete()
        );
        
        log.info("User registered successfully with email: {}", request.getEmail());

        // Generate tokens and set cookies
        AuthResponse authResponse = authService.generateTokensForUser(user);

        // Set access token cookie
        ResponseCookie accessTokenCookie = ResponseCookie
                .from(accessTokenCookieName, authResponse.getAccessToken())
                .httpOnly(true)
                .secure(true)
                .path("/")
                .sameSite("Lax")
                .maxAge(accessTokenExpiration / 1000)
                .build();
        response.addHeader("Set-Cookie", accessTokenCookie.toString());

        // Set refresh token cookie
        ResponseCookie refreshTokenCookie = ResponseCookie
                .from(refreshTokenCookieName, authResponse.getRefreshToken())
                .httpOnly(true)
                .secure(true)
                .path("/")
                .sameSite("Lax")
                .maxAge(refreshTokenExpiration / 1000)
                .build();
        response.addHeader("Set-Cookie", refreshTokenCookie.toString());
        
        return ResponseEntity.ok(
            ApiResponse.success("User registered successfully", userResponse)
        );
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthResponse>> login(
            @Valid @RequestBody LoginRequest request,
            HttpServletResponse response) {
        log.info("Login request received for email: {}", request.getEmail());

        AuthResponse authResponse = authService.login(request);
        
        log.info("Login successful for email: {}", request.getEmail());

        // Set access token cookie
        ResponseCookie accessTokenCookie = ResponseCookie
                .from(accessTokenCookieName, authResponse.getAccessToken())
                .httpOnly(true)
                .secure(true)
                .path("/")
                .sameSite("Lax")
                .maxAge(accessTokenExpiration / 1000)
                .build();
        response.addHeader("Set-Cookie", accessTokenCookie.toString());

        // Set refresh token cookie
        ResponseCookie refreshTokenCookie = ResponseCookie
                .from(refreshTokenCookieName, authResponse.getRefreshToken())
                .httpOnly(true)
                .secure(true)
                .path("/")
                .sameSite("Lax")
                .maxAge(refreshTokenExpiration / 1000)
                .build();
        response.addHeader("Set-Cookie", refreshTokenCookie.toString());

        // Return response without tokens in body (tokens are in cookies)
        AuthResponse responseWithoutTokens = new AuthResponse(
            authResponse.getEmail(),
            null,
            null,
            authResponse.getRole(),
            authResponse.isProfileComplete(),
            authResponse.getUserProfile()
        );

        return ResponseEntity.ok(
            ApiResponse.success("Login successful", responseWithoutTokens)
        );
    }

    @PostMapping("/refresh")
    public ResponseEntity<ApiResponse<AuthResponse>> refreshToken(
            HttpServletRequest request,
            HttpServletResponse response) {
        log.info("Refresh token request received");

        String refreshToken = null;
        if (request.getCookies() != null) {
            refreshToken = Arrays.stream(request.getCookies())
                .filter(c -> c.getName().equals(refreshTokenCookieName))
                .map(Cookie::getValue)
                .findFirst()
                .orElse(null);
        }

        if (refreshToken == null) {
            throw new BadRequestException("Refresh token not found");
        }

        AuthResponse authResponse = authService.refreshToken(refreshToken);
        
        log.info("Token refresh successful");

        // Set access token cookie
        ResponseCookie accessTokenCookie = ResponseCookie
                .from(accessTokenCookieName, authResponse.getAccessToken())
                .httpOnly(true)
                .secure(true)
                .path("/")
                .sameSite("Lax")
                .maxAge(accessTokenExpiration / 1000)
                .build();
        response.addHeader("Set-Cookie", accessTokenCookie.toString());

        // Set refresh token cookie
        ResponseCookie refreshTokenCookie = ResponseCookie
                .from(refreshTokenCookieName, authResponse.getRefreshToken())
                .httpOnly(true)
                .secure(true)
                .path("/")
                .sameSite("Lax")
                .maxAge(refreshTokenExpiration / 1000)
                .build();
        response.addHeader("Set-Cookie", refreshTokenCookie.toString());

        // Return response without tokens in body
        AuthResponse responseWithoutTokens = new AuthResponse(
            authResponse.getEmail(),
            null,
            null,
            authResponse.getRole(),
            authResponse.isProfileComplete(),
            authResponse.getUserProfile()
        );

        return ResponseEntity.ok(
            ApiResponse.success("Token refreshed successfully", responseWithoutTokens)
        );
    }

    @PostMapping("/logout")
public ResponseEntity<ApiResponse<Void>> logout(HttpServletResponse response) {
    // Clear access token cookie
    ResponseCookie accessTokenCookie = ResponseCookie
            .from(accessTokenCookieName, null)
            .httpOnly(true)
            .secure(true)
            .path("/")
            .sameSite("Lax")
            .maxAge(0)
            .build();
    response.addHeader("Set-Cookie", accessTokenCookie.toString());

    // Clear refresh token cookie
    ResponseCookie refreshTokenCookie = ResponseCookie
            .from(refreshTokenCookieName, null)
            .httpOnly(true)
            .secure(true)
            .path("/")
            .sameSite("Lax")
            .maxAge(0)
            .build();
    response.addHeader("Set-Cookie", refreshTokenCookie.toString());

    return ResponseEntity.ok(ApiResponse.success("Logged out successfully", null));
}
}
