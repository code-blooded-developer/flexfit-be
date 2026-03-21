package com.harish.flexfit.repo;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.harish.flexfit.entity.Exercise;
import com.harish.flexfit.enums.MuscleGroup;
import com.harish.flexfit.enums.DifficultyLevel;


public interface ExerciseRepository extends JpaRepository<Exercise, UUID> {
    List<Exercise> findByPrimaryMuscle(MuscleGroup muscle);

    List<Exercise> findByDifficultyLevel(DifficultyLevel difficultyLevel);

    @Query("""
        SELECT DISTINCT e FROM Exercise e
        JOIN e.equipments eq
        WHERE eq.id IN :equipmentIds        
    """)
    List<Exercise> findByEquipmentIds(List<UUID> equipmentIds);
}
