package com.harish.flexfit.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.harish.flexfit.dto.response.ApiResponse;
import com.harish.flexfit.dto.response.ExerciseResponse;
import com.harish.flexfit.enums.DifficultyLevel;
import com.harish.flexfit.enums.ExerciseCategory;
import com.harish.flexfit.enums.MuscleGroup;
import com.harish.flexfit.service.ExerciseService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/v1/exercises")
@Slf4j
public class ExerciseController {
    @Autowired
    private ExerciseService exerciseService;

     @GetMapping
    public ResponseEntity<ApiResponse<List<ExerciseResponse>>> filterExercises(
        @RequestParam(required = false) MuscleGroup muscle,
        @RequestParam(required = false) DifficultyLevel difficulty,
        @RequestParam(required = false) ExerciseCategory category,
        @RequestParam(required = false) List<UUID> equipmentIds
    ) {
        log.info("Filter exercises | muscle={}, difficulty={}, category={}, equipmentIds={}",
                muscle, difficulty, category, equipmentIds);

        List<ExerciseResponse> exercises = exerciseService.filterExercises(muscle, difficulty, equipmentIds, category);

        log.info("Filter result count={}", exercises.size());

        return ResponseEntity.ok(
                ApiResponse.success("Filtered exercises fetched successfully", exercises)
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ExerciseResponse>> getExerciseById(@PathVariable UUID id) {
        log.info("Fetching exercise for id={}", id);

        ExerciseResponse exercise = exerciseService.getExerciseById(id);

        log.info("Exercise fetched successfully id={}", id);

        return ResponseEntity.ok(
            ApiResponse.success("Exercise fetched successfully", exercise)
        );
    }
}
