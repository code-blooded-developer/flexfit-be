package com.harish.flexfit.dto.response;

import java.time.LocalDate;
import java.util.UUID;

import com.harish.flexfit.enums.ActivityLevel;
import com.harish.flexfit.enums.FitnessGoal;
import com.harish.flexfit.enums.Gender;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserProfileResponse {
    private UUID userId;
    private String name;
    private Gender gender;
    private LocalDate dateOfBirth;

    private Double heightInCm;
    private Double weightInKg;

    private FitnessGoal fitnessGoal;
    private ActivityLevel activityLevel;
}
