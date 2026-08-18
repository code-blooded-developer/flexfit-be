package com.harish.flexfit.service.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.harish.flexfit.dto.response.ExerciseResponse;
import com.harish.flexfit.entity.Equipment;
import com.harish.flexfit.entity.Exercise;
import com.harish.flexfit.enums.DifficultyLevel;
import com.harish.flexfit.enums.ExerciseCategory;
import com.harish.flexfit.enums.MuscleGroup;
import com.harish.flexfit.exception.BadRequestException;
import com.harish.flexfit.repo.ExerciseRepository;
import com.harish.flexfit.service.ExerciseService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ExerciseServiceImpl implements ExerciseService {
    @Autowired
    private ExerciseRepository exerciseRepository;

    @Override
    public List<ExerciseResponse> getAllExercises() {
        log.info("Fetching all exercises");

        List<Exercise> exercises = exerciseRepository.findAll();

        return exercises.stream()
                .map(this :: map)
                .toList();
    }

    @Override
    public ExerciseResponse getExerciseById(UUID id) {
        log.info("Fetching exercise with id={}", id);

        Exercise exercise = exerciseRepository.findById(id)
                            .orElseThrow(() -> {
                                log.error("Exercise with id={} not found", id);
                                return new BadRequestException("Exercise not found");
                            });

        return map(exercise);
    }

    @Override
    public List<ExerciseResponse> filterExercises(MuscleGroup muscle, DifficultyLevel difficulty,
            List<UUID> equipmentIds, ExerciseCategory category) {
        log.info("Filtering exercises: muscle={}, difficulty={}, equipmentIds={}, category={}",
                muscle, difficulty, equipmentIds, category);
        
        List<Exercise> exercises = exerciseRepository.findAll();

        if(muscle != null) {
            exercises = exercises.stream()
                .filter(exercise -> exercise.getPrimaryMuscle() == muscle)
                .toList();
        }

        if(difficulty != null) {
            exercises = exercises.stream()
                .filter(exercise -> exercise.getDifficultyLevel() == difficulty)
                .toList();
        }

        if(category != null) {
            exercises = exercises.stream()
                .filter(exercise -> exercise.getCategory() == category)
                .toList();
        }

        if(equipmentIds != null && !equipmentIds.isEmpty()) {
            exercises = exercises.stream()
                .filter(exercise -> exercise.getEquipments().stream()
                    .anyMatch(eq -> equipmentIds.contains(eq.getId())))
                .toList();
        }

        log.info("Filtered exercises count={}", exercises.size());

        return exercises.stream()
                .map(this::map)
                .toList();
    }

    private ExerciseResponse map(Exercise exercise) {
        List<String> equipmentNames = exercise.getEquipments() == null
                                    ? List.of()
                                    : exercise.getEquipments().stream()
                                        .map(Equipment :: getName)
                                        .toList();
                                    
        return ExerciseResponse.builder()
                .id(exercise.getId())
                .name(exercise.getName())
                .primaryMuscle(exercise.getPrimaryMuscle())
                .secondaryMuscles(exercise.getSecondaryMuscles())
                .difficultyLevel(exercise.getDifficultyLevel())
                .exerciseType(exercise.getExerciseType())
                .category(exercise.getCategory())
                .measurementType(exercise.getMeasurementType())
                .equipmentNames(equipmentNames)
                .mediaUrl(exercise.getMediaUrl())
                .mediaType(exercise.getMediaType())
                .build();
    }
}
