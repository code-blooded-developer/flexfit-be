package com.harish.flexfit.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.harish.flexfit.dto.request.AddExercisesRequest;
import com.harish.flexfit.dto.request.LogSetRequest;
import com.harish.flexfit.dto.response.ApiResponse;
import com.harish.flexfit.dto.response.WorkoutExerciseResponse;
import com.harish.flexfit.dto.response.WorkoutResponse;
import com.harish.flexfit.dto.response.WorkoutSetResponse;
import com.harish.flexfit.enums.ExerciseCategory;
import com.harish.flexfit.enums.MuscleGroup;
import com.harish.flexfit.service.WorkoutService;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("/api/v1/workouts")
public class WorkoutController {

    @Autowired
    private WorkoutService workoutService;

    @GetMapping("/today")
    public ResponseEntity<ApiResponse<WorkoutResponse>> getTodayWorkout() {
        UUID userId = getCurrentUserId();
        log.info("Received today's workout request for userId={}", userId);
        WorkoutResponse workout = workoutService.getTodayWorkout(userId);
        return ResponseEntity.ok(ApiResponse.success("Today's workout", workout));
    }

    @GetMapping("/history")
    public ResponseEntity<ApiResponse<List<WorkoutResponse>>> getWorkoutHistory() {
        UUID userId = getCurrentUserId();
        log.info("Received workout history request for userId={}", userId);
        List<WorkoutResponse> history = workoutService.getWorkoutHistory(userId);
        return ResponseEntity.ok(ApiResponse.success(history));
    }

    @GetMapping("/{workoutId}")
    public ResponseEntity<ApiResponse<WorkoutResponse>> getWorkoutById(
            @PathVariable UUID workoutId) {
        UUID userId = getCurrentUserId();
        log.info("Received workout detail request for workoutId={}", workoutId);
        WorkoutResponse workout = workoutService.getWorkoutById(userId, workoutId);
        return ResponseEntity.ok(ApiResponse.success(workout));
    }

    @PostMapping("/{workoutId}/complete")
    public ResponseEntity<ApiResponse<WorkoutResponse>> completeWorkout(
            @PathVariable UUID workoutId) {
        UUID userId = getCurrentUserId();
        log.info("Received complete workout request for workoutId={}", workoutId);
        WorkoutResponse workout = workoutService.completeWorkout(userId, workoutId);
        return ResponseEntity.ok(ApiResponse.success("Workout completed", workout));
    }

    @PostMapping("/{workoutId}/skip")
    public ResponseEntity<ApiResponse<WorkoutResponse>> skipWorkout(
            @PathVariable UUID workoutId) {
        UUID userId = getCurrentUserId();
        log.info("Received skip workout request for workoutId={}", workoutId);
        WorkoutResponse workout = workoutService.skipWorkout(userId, workoutId);
        return ResponseEntity.ok(ApiResponse.success("Workout skipped", workout));
    }

    @PostMapping("/{workoutId}/exercises")
    public ResponseEntity<ApiResponse<WorkoutResponse>> addExercise(
            @PathVariable UUID workoutId,
            @Valid @RequestBody AddExercisesRequest request) {
        UUID userId = getCurrentUserId();
        log.info("Received add exercise request for workoutId={}", workoutId);
        WorkoutResponse workout = workoutService.addExercise(userId, workoutId, request.getExerciseIds());
        return ResponseEntity.ok(ApiResponse.success("Exercises added", workout));
    }

    @DeleteMapping("/{workoutId}/exercises/{exerciseId}")
    public ResponseEntity<ApiResponse<WorkoutResponse>> removeExercise(
            @PathVariable UUID workoutId,
            @PathVariable UUID exerciseId) {
        UUID userId = getCurrentUserId();
        log.info("Received remove exercise request for workoutId={}, exerciseId={}",
                workoutId, exerciseId);
        WorkoutResponse workout = workoutService.removeExercise(userId, workoutId, exerciseId);
        return ResponseEntity.ok(ApiResponse.success("Exercise removed", workout));
    }

    @GetMapping("/{workoutId}/alternatives")
    public ResponseEntity<ApiResponse<List<WorkoutExerciseResponse>>> getAlternatives(
            @PathVariable UUID workoutId,
            @RequestParam(required = false) ExerciseCategory category,
            @RequestParam(required = false) MuscleGroup muscle) {
        UUID userId = getCurrentUserId();
        log.info("Received alternatives request for workoutId={}, category={}, muscle={}",
                workoutId, category, muscle);
        List<WorkoutExerciseResponse> alternatives =
                workoutService.getAlternativeExercises(userId, workoutId, category, muscle);
        return ResponseEntity.ok(ApiResponse.success(alternatives));
    }

    @PutMapping("/{workoutId}/exercises/{exerciseId}/replace/{alternativeId}")
    public ResponseEntity<ApiResponse<WorkoutResponse>> replaceExercise(
            @PathVariable UUID workoutId,
            @PathVariable UUID exerciseId,
            @PathVariable UUID alternativeId) {
        UUID userId = getCurrentUserId();
        log.info("Received replace exercise request for workoutId={}, exerciseId={}, alternativeId={}",
                workoutId, exerciseId, alternativeId);
        WorkoutResponse workout =
                workoutService.replaceExercise(userId, workoutId, exerciseId, alternativeId);
        return ResponseEntity.ok(ApiResponse.success("Exercise replaced", workout));
    }

    @PostMapping("/{workoutId}/exercises/{exerciseId}/sets")
    public ResponseEntity<ApiResponse<WorkoutSetResponse>> logSet(
            @PathVariable UUID workoutId,
            @PathVariable UUID exerciseId,
            @Valid @RequestBody LogSetRequest request) {
        UUID userId = getCurrentUserId();
        log.info("Received log set request for workoutId={}, exerciseId={}", workoutId, exerciseId);
        WorkoutSetResponse set = workoutService.logSet(userId, workoutId, exerciseId, request);
        return ResponseEntity.ok(ApiResponse.success("Set logged", set));
    }

    @PutMapping("/{workoutId}/exercises/{exerciseId}/sets/{setId}")
    public ResponseEntity<ApiResponse<WorkoutSetResponse>> updateSet(
            @PathVariable UUID workoutId,
            @PathVariable UUID exerciseId,
            @PathVariable UUID setId,
            @Valid @RequestBody LogSetRequest request) {
        UUID userId = getCurrentUserId();
        log.info("Received update set request for setId={}", setId);
        WorkoutSetResponse set =
                workoutService.updateSet(userId, workoutId, exerciseId, setId, request);
        return ResponseEntity.ok(ApiResponse.success("Set updated", set));
    }

    @DeleteMapping("/{workoutId}")
    public ResponseEntity<ApiResponse<Void>> deleteWorkout(
            @PathVariable UUID workoutId) {
        UUID userId = getCurrentUserId();
        log.info("Received delete workout request for workoutId={}", workoutId);
        workoutService.deleteWorkout(userId, workoutId);
        return ResponseEntity.ok(ApiResponse.success("Workout deleted", null));
    }

    @DeleteMapping("/{workoutId}/exercises/{exerciseId}/sets/{setId}")
    public ResponseEntity<ApiResponse<Void>> deleteSet(
            @PathVariable UUID workoutId,
            @PathVariable UUID exerciseId,
            @PathVariable UUID setId) {
        UUID userId = getCurrentUserId();
        log.info("Received delete set request for setId={}", setId);
        workoutService.deleteSet(userId, workoutId, exerciseId, setId);
        return ResponseEntity.ok(ApiResponse.success("Set deleted", null));
    }

    private UUID getCurrentUserId() {
        return (UUID) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
