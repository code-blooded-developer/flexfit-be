package com.harish.flexfit.service.impl;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.harish.flexfit.dto.request.UserProfileRequest;
import com.harish.flexfit.dto.response.UserProfileResponse;
import com.harish.flexfit.entity.UserProfile;
import com.harish.flexfit.exception.BadRequestException;
import com.harish.flexfit.repo.UserProfileRepository;
import com.harish.flexfit.service.UserProfileService;
import com.harish.flexfit.util.UnitConversionUtil;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserProfileServiceImpl implements UserProfileService {
    @Autowired
    private UserProfileRepository userProfileRepository;

    @Override
    public UserProfileResponse createOrUpdateProfile(UUID userId, UserProfileRequest request) {
        log.info("Processing profile for userId={}", userId);

        UserProfile profile = userProfileRepository.findByUserId(userId)
                .orElse(UserProfile.builder().userId(userId).build());

        double heightInCm = UnitConversionUtil.convertHeightToCm(request.getHeight(), request.getHeightUnit());

        double weightInKg = UnitConversionUtil.convertWeightToKg(request.getWeight(), request.getWeightUnit());

        profile.setName(request.getName());
        profile.setGender(request.getGender());
        profile.setDateOfBirth(request.getDateOfBirth());
        profile.setHeight(request.getHeight());
        profile.setHeightUnit(request.getHeightUnit());
        profile.setHeightInCm(heightInCm);
        profile.setWeight(request.getWeight());
        profile.setWeightUnit(request.getWeightUnit());
        profile.setWeightInKg(weightInKg);
        profile.setFitnessGoal(request.getFitnessGoal());
        profile.setActivityLevel(request.getActivityLevel());

        UserProfile saved = userProfileRepository.save(profile);

        log.info("Profile saved for userId={}", userId);

        return map(saved);
    }

    @Override
    public UserProfileResponse getProfile(UUID userId) {
        log.info("Fetching profile for userId={}", userId);

        UserProfile profile = userProfileRepository.findByUserId(userId)
                .orElseThrow(() -> new BadRequestException("Profile not found"));

        return map(profile);
    }

    private UserProfileResponse map(UserProfile profile) {
        return UserProfileResponse.builder()
                .userId(profile.getUserId())
                .name(profile.getName())
                .gender(profile.getGender())
                .dateOfBirth(profile.getDateOfBirth())
                .heightInCm(profile.getHeightInCm())
                .weightInKg(profile.getWeightInKg())
                .fitnessGoal(profile.getFitnessGoal())
                .activityLevel(profile.getActivityLevel())
                .build();
    }
}
