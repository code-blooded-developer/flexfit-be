package com.harish.flexfit.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.harish.flexfit.dto.request.LoginRequest;
import com.harish.flexfit.dto.request.RefreshTokenRequest;
import com.harish.flexfit.dto.request.SignupRequest;
import com.harish.flexfit.dto.response.ApiResponse;
import com.harish.flexfit.dto.response.AuthResponse;
import com.harish.flexfit.dto.response.UserResponse;
import com.harish.flexfit.entity.User;
import com.harish.flexfit.service.AuthService;
import com.harish.flexfit.service.UserService;

import jakarta.servlet.http.Cookie;
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

    @Value("${jwt.cookie-name:ACCESS_TOKEN}")
    private String accessTokenCookieName;

    @Value("${jwt.refresh-cookie-name:REFRESH_TOKEN}")
    private String refreshTokenCookieName;

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
        UserResponse userResponse = new UserResponse(user.getId(), user.getEmail(), user.getRole().name());
        
        log.info("User registered successfully with email: {}", request.getEmail());

        // Generate tokens and set cookies
        AuthResponse authResponse = authService.generateTokensForUser(user);

        // Set access token cookie
        Cookie accessTokenCookie = new Cookie(accessTokenCookieName, authResponse.getAccessToken());
        accessTokenCookie.setHttpOnly(true);
        accessTokenCookie.setSecure(true);
        accessTokenCookie.setPath("/");
        accessTokenCookie.setMaxAge((int) (accessTokenExpiration / 1000));
        accessTokenCookie.setAttribute("SameSite", "Lax");
        response.addCookie(accessTokenCookie);

        // Set refresh token cookie
        Cookie refreshTokenCookie = new Cookie(refreshTokenCookieName, authResponse.getRefreshToken());
        refreshTokenCookie.setHttpOnly(true);
        refreshTokenCookie.setSecure(true);
        refreshTokenCookie.setPath("/");
        refreshTokenCookie.setMaxAge((int) (refreshTokenExpiration / 1000));
        refreshTokenCookie.setAttribute("SameSite", "Lax");
        response.addCookie(refreshTokenCookie);
        
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
        Cookie accessTokenCookie = new Cookie(accessTokenCookieName, authResponse.getAccessToken());
        accessTokenCookie.setHttpOnly(true);
        accessTokenCookie.setSecure(true);
        accessTokenCookie.setPath("/");
        accessTokenCookie.setMaxAge((int) (accessTokenExpiration / 1000));
        accessTokenCookie.setAttribute("SameSite", "Lax");
        response.addCookie(accessTokenCookie);

        // Set refresh token cookie
        Cookie refreshTokenCookie = new Cookie(refreshTokenCookieName, authResponse.getRefreshToken());
        refreshTokenCookie.setHttpOnly(true);
        refreshTokenCookie.setSecure(true);
        refreshTokenCookie.setPath("/");
        refreshTokenCookie.setMaxAge((int) (refreshTokenExpiration / 1000));
        refreshTokenCookie.setAttribute("SameSite", "Lax");
        response.addCookie(refreshTokenCookie);

        // Return response without tokens in body (tokens are in cookies)
        AuthResponse responseWithoutTokens = new AuthResponse(
            authResponse.getEmail(),
            null,
            null,
            authResponse.getRole()
        );

        return ResponseEntity.ok(
            ApiResponse.success("Login successful", responseWithoutTokens)
        );
    }

    @PostMapping("/refresh")
    public ResponseEntity<ApiResponse<AuthResponse>> refreshToken(
            @Valid @RequestBody RefreshTokenRequest request,
            HttpServletResponse response) {
        log.info("Refresh token request received");

        AuthResponse authResponse = authService.refreshToken(request);
        
        log.info("Token refresh successful");

        // Set access token cookie
        Cookie accessTokenCookie = new Cookie(accessTokenCookieName, authResponse.getAccessToken());
        accessTokenCookie.setHttpOnly(true);
        accessTokenCookie.setSecure(true);
        accessTokenCookie.setPath("/");
        accessTokenCookie.setMaxAge((int) (accessTokenExpiration / 1000));
        accessTokenCookie.setAttribute("SameSite", "Lax");
        response.addCookie(accessTokenCookie);

        // Set refresh token cookie
        Cookie refreshTokenCookie = new Cookie(refreshTokenCookieName, authResponse.getRefreshToken());
        refreshTokenCookie.setHttpOnly(true);
        refreshTokenCookie.setSecure(true);
        refreshTokenCookie.setPath("/");
        refreshTokenCookie.setMaxAge((int) (refreshTokenExpiration / 1000));
        refreshTokenCookie.setAttribute("SameSite", "Lax");
        response.addCookie(refreshTokenCookie);

        // Return response without tokens in body
        AuthResponse responseWithoutTokens = new AuthResponse(
            authResponse.getEmail(),
            null,
            null,
            authResponse.getRole()
        );

        return ResponseEntity.ok(
            ApiResponse.success("Token refreshed successfully", responseWithoutTokens)
        );
    }
}
