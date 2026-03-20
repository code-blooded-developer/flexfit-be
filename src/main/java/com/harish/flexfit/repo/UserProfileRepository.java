package com.harish.flexfit.repo;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.harish.flexfit.entity.UserProfile;

public interface UserProfileRepository extends JpaRepository<UserProfile, UUID> {
    Optional<UserProfile> findByUser_Id(UUID userId);
}
