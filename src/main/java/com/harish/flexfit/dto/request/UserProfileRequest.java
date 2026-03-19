package com.harish.flexfit.dto.request;

import java.time.LocalDate;

import com.harish.flexfit.enums.ActivityLevel;
import com.harish.flexfit.enums.FitnessGoal;
import com.harish.flexfit.enums.Gender;
import com.harish.flexfit.enums.HeightUnit;
import com.harish.flexfit.enums.WeightUnit;
import com.harish.flexfit.validator.annotation.ValidUserProfile;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@ValidUserProfile
public class UserProfileRequest {
    @NotBlank(message = "Name is required")
    @Size(max = 100, message = "Name must be at most 100 characters long")
    private String name;

    @NotNull(message = "Gender is required")
    private Gender gender;

    @NotNull(message = "Date of birth is required")
    @Past(message = "Date of birth must be in the past")
    private LocalDate dateOfBirth;

    @NotNull(message = "Height is required")
    @Positive(message = "Height must be a positive number")
    private Double height;

    @NotNull(message = "Height unit is required")
    private HeightUnit heightUnit;

    @NotNull(message = "Weight is required")
    @Positive(message = "Weight must be a positive number")
    private Double weight;

    @NotNull(message = "Weight unit is required")
    private WeightUnit weightUnit;

    @NotNull(message = "Fitness goal is required")
    private FitnessGoal fitnessGoal;

    @NotNull(message = "Activity level is required")
    private ActivityLevel activityLevel;
}
