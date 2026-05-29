package com.harish.flexfit.repo;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.harish.flexfit.entity.Exercise;
import com.harish.flexfit.entity.WorkoutExercise;

public interface WorkoutExerciseRepository extends JpaRepository<WorkoutExercise, UUID> {
    List<WorkoutExercise> findByWorkout_IdOrderBySortOrder(UUID workoutId);

    @Query("""
        SELECT DISTINCT we.exercise FROM WorkoutExercise we
        JOIN we.workout w
        WHERE w.userProfile.id = :userProfileId
        AND w.date >= :since
    """)
    List<Exercise> findDistinctExercisesByUserProfileSince(
        UUID userProfileId, LocalDate since);
}
