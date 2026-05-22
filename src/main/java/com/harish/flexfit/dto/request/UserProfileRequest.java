package com.harish.flexfit.dto.request;

import java.time.LocalDate;

import com.harish.flexfit.enums.ExperienceLevel;
import com.harish.flexfit.enums.FitnessGoal;
import com.harish.flexfit.enums.Gender;
import com.harish.flexfit.enums.HeightUnit;
import com.harish.flexfit.enums.WeightUnit;
import com.harish.flexfit.enums.WorkoutEnvironment;
import com.harish.flexfit.validator.annotation.ValidUserProfile;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
@ValidUserProfile
public class UserProfileRequest {
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

    @NotNull(message = "Experience level is required")
    private ExperienceLevel experienceLevel;

    @NotNull(message = "Workout days per week is required")
    @Min(value = 1, message = "Workout days per week must be at least 1")
    @Max(value = 7, message = "Workout days per week must be at most 7")
    private Integer workoutDaysPerWeek;

    @NotNull(message = "Workout duration is required")
    @Min(value = 15, message = "Workout duration must be at least 15 minutes")
    @Max(value = 180, message = "Workout duration must be at most 180 minutes")
    private Integer workoutDurationMinutes;

    @NotNull(message = "Workout environment is required")
    private WorkoutEnvironment workoutEnvironment;
}
