package com.harish.flexfit.entity.dto;

import java.util.UUID;

import lombok.Data;

@Data
public class UserResponseDTO {
    private UUID id;
    private String email;
}
