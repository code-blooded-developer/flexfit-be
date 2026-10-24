package com.harish.flexfit.entity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.harish.flexfit.enums.DifficultyLevel;
import com.harish.flexfit.enums.ExerciseCategory;
import com.harish.flexfit.enums.ExerciseType;
import com.harish.flexfit.enums.MeasurementType;
import com.harish.flexfit.enums.MuscleGroup;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(
    name = "exercises"
)
public class Exercise {
    @Id
    @GeneratedValue
    @Column(columnDefinition = "uuid", updatable = false, nullable = false)
    private UUID id;

    @Column(nullable = false, unique = true)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MuscleGroup primaryMuscle;

    @Enumerated(EnumType.STRING)
    @CollectionTable(
        name = "exercise_secondary_muscles",
        joinColumns = @JoinColumn(name = "exercise_id")
    )
    private List<MuscleGroup> secondaryMuscles;

    @Enumerated(EnumType.STRING)
    private DifficultyLevel difficultyLevel;

    @Enumerated(EnumType.STRING)
    private ExerciseType exerciseType;

    @Enumerated(EnumType.STRING)
    private ExerciseCategory category;

    @Enumerated(EnumType.STRING)
    private MeasurementType measurementType;

    @ManyToMany
    @JoinTable(
        name = "exercise_equipment",
        joinColumns = @JoinColumn(name = "exercise_id"),
        inverseJoinColumns = @JoinColumn(name = "equipment_id")
    )
    private List<Equipment> equipments;

    @Column(columnDefinition = "text")
    private String instructions;

    @Column(name = "thumbnail_key")
    private String thumbnailKey;

    @Column(name = "video_key")
    private String videoKey;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
