package com.harish.flexfit.service.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.harish.flexfit.dto.request.UserProfileRequest;
import com.harish.flexfit.dto.response.UserProfileResponse;
import com.harish.flexfit.entity.Equipment;
import com.harish.flexfit.entity.User;
import com.harish.flexfit.entity.UserProfile;
import com.harish.flexfit.entity.WeightLog;
import com.harish.flexfit.exception.BadRequestException;
import com.harish.flexfit.repo.EquipmentRepository;
import com.harish.flexfit.repo.UserProfileRepository;
import com.harish.flexfit.repo.UserRepository;
import com.harish.flexfit.repo.WeightLogRepository;
import com.harish.flexfit.service.UserProfileService;
import com.harish.flexfit.util.UnitConversionUtil;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserProfileServiceImpl implements UserProfileService {
    @Autowired
    private UserProfileRepository userProfileRepository;
    
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EquipmentRepository equipmentRepository;

    @Autowired
    private WeightLogRepository weightLogRepository;

    @Override
    public UserProfileResponse createOrUpdateProfile(UUID userId, UserProfileRequest request) {
        log.info("Processing profile for userId={}", userId);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BadRequestException("User not found"));

        UserProfile profile = userProfileRepository.findByUser_Id(userId)
                .orElse(UserProfile.builder().user(user).build());

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
        profile.setExperienceLevel(request.getExperienceLevel());

        profile.setWorkoutDaysPerWeek(request.getWorkoutDaysPerWeek());
        profile.setWorkoutDurationMinutes(request.getWorkoutDurationMinutes());
        profile.setWorkoutEnvironment(request.getWorkoutEnvironment());

        if(request.getEquipmentIds() != null) {
            List<Equipment> equipments = equipmentRepository.findAllById(request.getEquipmentIds());
            profile.setEquipments(equipments);
        }

        UserProfile saved = userProfileRepository.save(profile);

        WeightLog weightLog = new WeightLog();
        weightLog.setUserId(userId);
        weightLog.setWeight(request.getWeight());
        weightLog.setWeightInKg(weightInKg);
        weightLogRepository.save(weightLog);

        if(!user.isProfileComplete()) {
            user.setProfileComplete(true);
            userRepository.save(user);
        }

        log.info("Profile saved for userId={}", userId);

        return map(saved);
    }

    @Override
    public UserProfileResponse getProfile(UUID userId) {
        log.info("Fetching profile for userId={}", userId);

        UserProfile profile = userProfileRepository.findByUser_Id(userId)
                .orElseThrow(() -> new BadRequestException("Profile not found"));

        return map(profile);
    }

    private UserProfileResponse map(UserProfile profile) {
        return UserProfileResponse.builder()
                .userId(profile.getUser().getId())
                .name(profile.getName())
                .gender(profile.getGender())
                .dateOfBirth(profile.getDateOfBirth())
                .heightInCm(profile.getHeightInCm())
                .weightInKg(profile.getWeightInKg())
                .fitnessGoal(profile.getFitnessGoal())
                .experienceLevel(profile.getExperienceLevel())
                .workoutDaysPerWeek(profile.getWorkoutDaysPerWeek())
                .workoutDurationMinutes(profile.getWorkoutDurationMinutes())
                .workoutEnvironment(profile.getWorkoutEnvironment())
                .build();
    }
}
