package com.harish.flexfit.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import com.harish.flexfit.enums.ActivityLevel;
import com.harish.flexfit.enums.FitnessGoal;
import com.harish.flexfit.enums.Gender;
import com.harish.flexfit.enums.HeightUnit;
import com.harish.flexfit.enums.WeightUnit;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(
    name = "user_profile",
    indexes = {
        @Index(name = "idx_user_profile_user_id", columnList = "user_id")
    }
)
public class UserProfile {
    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false, unique = true)
    private UUID userId;

    private String name;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private LocalDate dateOfBirth;

    private Double height;

    @Enumerated(EnumType.STRING)
    private HeightUnit heightUnit;

    private Double heightInCm;

    private Double weight;

    @Enumerated(EnumType.STRING)
    private WeightUnit weightUnit;

    private Double weightInKg;

    @Enumerated(EnumType.STRING)
    private FitnessGoal fitnessGoal;

    @Enumerated(EnumType.STRING)
    private ActivityLevel activityLevel;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
