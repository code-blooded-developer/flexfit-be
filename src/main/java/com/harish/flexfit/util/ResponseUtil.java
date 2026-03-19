package com.harish.flexfit.util;

import org.springframework.http.ResponseEntity;

import com.harish.flexfit.dto.response.ApiResponse;

public class ResponseUtil {
    public static <T> ResponseEntity<ApiResponse<T>> success (int status, String message, T data) {
        return ResponseEntity.status(status)
                .body(new ApiResponse<>(status, message, data));
    }
}
