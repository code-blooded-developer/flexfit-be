package com.harish.flexfit.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.harish.flexfit.dto.request.UserProfileRequest;
import com.harish.flexfit.dto.request.UserProfileRequest.EquipmentSelection;
import com.harish.flexfit.dto.request.UserProfileRequest.VariantSelection;
import com.harish.flexfit.dto.response.UserEquipmentResponse;
import com.harish.flexfit.dto.response.UserEquipmentResponse.SelectedVariant;
import com.harish.flexfit.dto.response.UserProfileResponse;
import com.harish.flexfit.entity.Equipment;
import com.harish.flexfit.entity.EquipmentVariant;
import com.harish.flexfit.entity.User;
import com.harish.flexfit.entity.UserEquipment;
import com.harish.flexfit.entity.UserProfile;
import com.harish.flexfit.entity.WeightLog;
import com.harish.flexfit.exception.BadRequestException;
import com.harish.flexfit.repo.EquipmentRepository;
import com.harish.flexfit.repo.EquipmentVariantRepository;
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
    private EquipmentVariantRepository equipmentVariantRepository;

    @Autowired
    private WeightLogRepository weightLogRepository;

    @Override
    @Transactional
    public UserProfileResponse createOrUpdateProfile(UUID userId, UserProfileRequest request) {
        log.info("Processing profile for userId={}", userId);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BadRequestException("User not found"));

        UserProfile profile = userProfileRepository.findByUser_Id(userId)
                .orElse(UserProfile.builder().user(user).build());

        double heightInCm = UnitConversionUtil.convertHeightToCm(request.getHeight(), request.getHeightUnit());

        double weightInKg = UnitConversionUtil.convertWeightToKg(request.getWeight(), request.getWeightUnit());

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

        if (request.getEquipmentSelections() != null) {
            profile.getUserEquipments().clear();
            List<UserEquipment> userEquipments = buildUserEquipments(profile, request.getEquipmentSelections());
            profile.getUserEquipments().addAll(userEquipments);
        }

        UserProfile saved = userProfileRepository.save(profile);

        WeightLog weightLog = new WeightLog();
        weightLog.setUserId(userId);
        weightLog.setWeight(request.getWeight());
        weightLog.setWeightInKg(weightInKg);
        weightLogRepository.save(weightLog);

        if (!user.isProfileComplete()) {
            user.setProfileComplete(true);
            userRepository.save(user);
        }

        log.info("Profile saved for userId={}", userId);

        return map(saved, user.isProfileComplete());
    }

    @Override
    @Transactional(readOnly = true)
    public UserProfileResponse getProfile(UUID userId) {
        log.info("Fetching profile for userId={}", userId);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BadRequestException("User not found"));

        UserProfile profile = userProfileRepository.findByUser_Id(userId)
                .orElseThrow(() -> new BadRequestException("Profile not found"));

        return map(profile, user.isProfileComplete());
    }

    private List<UserEquipment> buildUserEquipments(UserProfile profile, List<EquipmentSelection> selections) {
        List<UserEquipment> result = new ArrayList<>();

        List<UUID> equipmentIds = selections.stream()
                .map(EquipmentSelection::getEquipmentId)
                .toList();
        List<Equipment> equipments = equipmentRepository.findAllById(equipmentIds);
        java.util.Map<UUID, Equipment> equipmentMap = equipments.stream()
                .collect(Collectors.toMap(Equipment::getId, e -> e));

        List<UUID> allVariantIds = selections.stream()
                .filter(s -> s.getVariantSelections() != null)
                .flatMap(s -> s.getVariantSelections().stream())
                .map(VariantSelection::getVariantId)
                .toList();
        List<EquipmentVariant> variants = allVariantIds.isEmpty()
                ? List.of()
                : equipmentVariantRepository.findAllById(allVariantIds);
        java.util.Map<UUID, EquipmentVariant> variantMap = variants.stream()
                .collect(Collectors.toMap(EquipmentVariant::getId, v -> v));

        for (EquipmentSelection selection : selections) {
            Equipment equipment = equipmentMap.get(selection.getEquipmentId());
            if (equipment == null) {
                log.warn("Equipment {} not found, skipping", selection.getEquipmentId());
                continue;
            }

            if (selection.getVariantSelections() != null && !selection.getVariantSelections().isEmpty()) {
                for (VariantSelection vs : selection.getVariantSelections()) {
                    EquipmentVariant variant = variantMap.get(vs.getVariantId());
                    if (variant == null) {
                        log.warn("Variant {} not found, skipping", vs.getVariantId());
                        continue;
                    }
                    UserEquipment ue = new UserEquipment();
                    ue.setUserProfile(profile);
                    ue.setEquipment(equipment);
                    ue.setVariant(variant);
                    ue.setQuantity(vs.getQuantity());
                    result.add(ue);
                }
            } else {
                UserEquipment ue = new UserEquipment();
                ue.setUserProfile(profile);
                ue.setEquipment(equipment);
                ue.setQuantity(1);
                result.add(ue);
            }
        }

        return result;
    }

    private UserProfileResponse map(UserProfile profile, boolean isProfileComplete) {
        List<UserEquipmentResponse> equipmentResponses = null;
        if (profile.getUserEquipments() != null) {
            java.util.Map<UUID, List<UserEquipment>> byEquipment = profile.getUserEquipments().stream()
                    .collect(Collectors.groupingBy(ue -> ue.getEquipment().getId()));

            equipmentResponses = byEquipment.entrySet().stream()
                    .map(entry -> {
                        List<UserEquipment> ueList = entry.getValue();
                        UserEquipment first = ueList.get(0);
                        Equipment eq = first.getEquipment();

                        List<SelectedVariant> selectedVariants = ueList.stream()
                                .filter(ue -> ue.getVariant() != null)
                                .map(ue -> SelectedVariant.builder()
                                        .variantId(ue.getVariant().getId())
                                        .weight(ue.getVariant().getWeight())
                                        .weightUnit(ue.getVariant().getWeightUnit())
                                        .quantity(ue.getQuantity())
                                        .build())
                                .toList();

                        return UserEquipmentResponse.builder()
                                .id(eq.getId())
                                .name(eq.getName())
                                .type(eq.getType())
                                .category(eq.getCategory())
                                .hasVariants(eq.isHasVariants())
                                .selectedVariants(selectedVariants)
                                .build();
                    })
                    .toList();
        }

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
                .equipments(equipmentResponses)
                .isProfileComplete(isProfileComplete)
                .build();
    }
}
