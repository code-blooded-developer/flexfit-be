package com.harish.flexfit.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.harish.flexfit.dto.response.ErrorResponse;

import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(MethodArgumentNotValidException ex) {
         String errorMessage = ex.getBindingResult()
            .getFieldErrors()
            .stream()
            .map(err -> err.getField() + ": " + err.getDefaultMessage())
            .findFirst()
            .orElse("Validation error");
        
        log.warn("Validation error: {}", errorMessage);

        ErrorResponse errorResponse = new ErrorResponse(400, "Bad Request", errorMessage);

        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorResponse> handleBadRequestException(BadRequestException ex) {
        log.warn("Business validation failed: {}", ex.getMessage());
        ErrorResponse errorResponse = new ErrorResponse(400, "Bad Request", ex.getMessage());
        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception ex) {
        log.error("Unexpected error occurred", ex);
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Internal Server Error", "Something went wrong");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }
}
