package com.harish.flexfit.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.harish.flexfit.dto.request.EquipmentSelectionRequest;
import com.harish.flexfit.dto.request.EquipmentSelectionRequest.EquipmentSelection;
import com.harish.flexfit.dto.request.EquipmentSelectionRequest.VariantSelection;
import com.harish.flexfit.dto.response.EquipmentSelectionResponse;
import com.harish.flexfit.dto.response.EquipmentVariantResponse;
import com.harish.flexfit.entity.Equipment;
import com.harish.flexfit.entity.EquipmentVariant;
import com.harish.flexfit.entity.UserEquipment;
import com.harish.flexfit.entity.UserProfile;
import com.harish.flexfit.enums.EquipmentType;
import com.harish.flexfit.enums.WorkoutEnvironment;
import com.harish.flexfit.exception.BadRequestException;
import com.harish.flexfit.repo.EquipmentRepository;
import com.harish.flexfit.repo.EquipmentVariantRepository;
import com.harish.flexfit.repo.UserProfileRepository;
import com.harish.flexfit.service.EquipmentService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class EquipmentServiceImpl implements EquipmentService {

    @Autowired
    private EquipmentRepository equipmentRepository;

    @Autowired
    private EquipmentVariantRepository equipmentVariantRepository;

    @Autowired
    private UserProfileRepository userProfileRepository;

    @Override
    public List<EquipmentSelectionResponse> getEquipmentForEnvironment(WorkoutEnvironment environment, UUID userId) {
        log.info("Fetching equipment for environment={}, userId={}", environment, userId);

        UserProfile profile = userProfileRepository.findByUser_Id(userId).orElse(null);

        List<Equipment> allEquipment = equipmentRepository.findAll();

        List<EquipmentVariant> allVariants = equipmentVariantRepository.findAll();
        Map<UUID, List<EquipmentVariant>> variantsByEquipment = allVariants.stream()
                .collect(Collectors.groupingBy(v -> v.getEquipment().getId()));

        List<UUID> userEquipmentIds = Collections.emptyList();
        Map<UUID, List<UUID>> userVariantIdsByEquipment = Collections.emptyMap();

        if (profile != null && profile.getUserEquipments() != null) {
            List<UserEquipment> userEquipments = profile.getUserEquipments();
            userEquipmentIds = userEquipments.stream()
                    .map(ue -> ue.getEquipment().getId())
                    .distinct()
                    .toList();
            userVariantIdsByEquipment = userEquipments.stream()
                    .filter(ue -> ue.getVariant() != null)
                    .collect(Collectors.groupingBy(
                            ue -> ue.getEquipment().getId(),
                            Collectors.mapping(ue -> ue.getVariant().getId(), Collectors.toList())
                    ));
        }

        Map<UUID, List<UUID>> finalUserVariantIdsByEquipment = userVariantIdsByEquipment;
        List<UUID> finalUserEquipmentIds = userEquipmentIds;

        return allEquipment.stream()
                .map(eq -> {
                    boolean selected = !finalUserEquipmentIds.isEmpty()
                            ? finalUserEquipmentIds.contains(eq.getId())
                            : isDefaultForEnvironment(eq, environment);

                    List<EquipmentVariant> variants = variantsByEquipment.getOrDefault(eq.getId(), List.of());
                    List<EquipmentVariantResponse> variantResponses = variants.stream()
                            .map(v -> EquipmentVariantResponse.builder()
                                    .id(v.getId())
                                    .weight(v.getWeight())
                                    .weightUnit(v.getWeightUnit())
                                    .build())
                            .toList();

                    List<UUID> selectedVariantIds = selected
                            ? finalUserVariantIdsByEquipment.getOrDefault(eq.getId(),
                                    variants.stream().map(EquipmentVariant::getId).toList())
                            : List.of();

                    return EquipmentSelectionResponse.builder()
                            .id(eq.getId())
                            .name(eq.getName())
                            .type(eq.getType())
                            .category(eq.getCategory())
                            .hasVariants(eq.isHasVariants())
                            .variants(variantResponses)
                            .selected(selected)
                            .selectedVariantIds(selectedVariantIds)
                            .build();
                })
                .toList();
    }

    @Override
    @Transactional
    public void saveEquipmentSelections(UUID userId, EquipmentSelectionRequest request) {
        log.info("Saving equipment selections for userId={}", userId);

        UserProfile profile = userProfileRepository.findByUser_Id(userId)
                .orElseThrow(() -> new BadRequestException("Profile not found. Complete profile first."));

        List<EquipmentSelection> selections = request.getEquipmentSelections();
        if (selections == null) {
            profile.getUserEquipments().clear();
            userProfileRepository.save(profile);
            return;
        }

        List<UUID> equipmentIds = selections.stream()
                .map(EquipmentSelection::getEquipmentId)
                .toList();
        List<Equipment> equipments = equipmentRepository.findAllById(equipmentIds);
        Map<UUID, Equipment> equipmentMap = equipments.stream()
                .collect(Collectors.toMap(Equipment::getId, e -> e));

        List<UUID> allVariantIds = selections.stream()
                .filter(s -> s.getVariantSelections() != null)
                .flatMap(s -> s.getVariantSelections().stream())
                .map(VariantSelection::getVariantId)
                .toList();
        Map<UUID, EquipmentVariant> variantMap = !allVariantIds.isEmpty()
                ? equipmentVariantRepository.findAllById(allVariantIds).stream()
                        .collect(Collectors.toMap(EquipmentVariant::getId, v -> v))
                : Collections.emptyMap();

        List<UserEquipment> userEquipments = new ArrayList<>();

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
                    userEquipments.add(ue);
                }
            } else {
                UserEquipment ue = new UserEquipment();
                ue.setUserProfile(profile);
                ue.setEquipment(equipment);
                ue.setQuantity(1);
                userEquipments.add(ue);
            }
        }

        profile.getUserEquipments().clear();
        profile.getUserEquipments().addAll(userEquipments);
        userProfileRepository.save(profile);

        log.info("Equipment selections saved for userId={}", userId);
    }

    private boolean isDefaultForEnvironment(Equipment eq, WorkoutEnvironment env) {
        return switch (env) {
            case GYM -> true;
            case HOME -> {
                EquipmentType t = eq.getType();
                yield t == EquipmentType.BODYWEIGHT
                        || t == EquipmentType.FREE_WEIGHT
                        || t == EquipmentType.RESISTANCE
                        || t == EquipmentType.MOBILITY
                        || t == EquipmentType.SUSPENSION
                        || t == EquipmentType.FUNCTIONAL;
            }
            case SOCIETY_GYM -> eq.getType() != EquipmentType.CARDIO_MACHINE
                    && eq.getType() != EquipmentType.AQUATIC;
        };
    }
}
