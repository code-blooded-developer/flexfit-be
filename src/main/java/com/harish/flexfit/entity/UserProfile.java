package com.harish.flexfit.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.harish.flexfit.enums.ExperienceLevel;
import com.harish.flexfit.enums.FitnessGoal;
import com.harish.flexfit.enums.Gender;
import com.harish.flexfit.enums.HeightUnit;
import com.harish.flexfit.enums.WeightUnit;
import com.harish.flexfit.enums.WorkoutEnvironment;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(
    name = "user_profile",
    indexes = {
        @Index(name = "idx_user_profile_user_id", columnList = "user_id")
    }
)
public class UserProfile {
    @Id
    @GeneratedValue
    @Column(columnDefinition = "uuid", updatable = false)
    private UUID id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    @Column(nullable = false)
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
    private ExperienceLevel experienceLevel;

    private Integer workoutDaysPerWeek; 
    private Integer workoutDurationMinutes;

    @Enumerated(EnumType.STRING)
    private WorkoutEnvironment workoutEnvironment;

    @ManyToMany
    @JoinTable(
        name = "user_equipment",
        joinColumns = @JoinColumn(name = "user_profile_id"),
        inverseJoinColumns = @JoinColumn(name = "equipment_id")
    )
    private List<Equipment> equipments;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
