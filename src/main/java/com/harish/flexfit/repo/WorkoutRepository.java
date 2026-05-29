package com.harish.flexfit.repo;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.harish.flexfit.entity.Workout;

public interface WorkoutRepository extends JpaRepository<Workout, UUID> {
    Optional<Workout> findByUserProfile_IdAndDate(UUID userProfileId, LocalDate date);

    List<Workout> findByUserProfile_IdAndDateBetweenOrderByDateDesc(
        UUID userProfileId, LocalDate start, LocalDate end);

    List<Workout> findByUserProfile_IdOrderByDateDesc(UUID userProfileId);

    long countByUserProfile_Id(UUID userProfileId);
}
