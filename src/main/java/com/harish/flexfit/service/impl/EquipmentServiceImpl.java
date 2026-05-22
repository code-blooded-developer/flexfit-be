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

import com.harish.flexfit.dto.request.EquipmentRequest;
import com.harish.flexfit.dto.request.EquipmentSelectionRequest;
import com.harish.flexfit.dto.request.EquipmentSelectionRequest.EquipmentSelection;
import com.harish.flexfit.dto.request.EquipmentSelectionRequest.VariantSelection;
import com.harish.flexfit.dto.response.EquipmentResponse;
import com.harish.flexfit.dto.response.EquipmentSelectionResponse;
import com.harish.flexfit.dto.response.EquipmentVariantResponse;
import com.harish.flexfit.dto.response.UserEquipmentResponse;
import com.harish.flexfit.dto.response.UserEquipmentResponse.SelectedVariant;
import com.harish.flexfit.entity.Equipment;
import com.harish.flexfit.entity.EquipmentVariant;
import com.harish.flexfit.entity.UserEquipment;
import com.harish.flexfit.entity.UserProfile;
import com.harish.flexfit.enums.EquipmentType;
import com.harish.flexfit.enums.WorkoutEnvironment;
import com.harish.flexfit.exception.BadRequestException;
import com.harish.flexfit.exception.ConflictException;
import com.harish.flexfit.exception.ResourceNotFoundException;
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
    public List<EquipmentResponse> getEquipments() {
        log.info("Fetching all equipment");

        List<Equipment> allEquipment = equipmentRepository.findAll();
        List<EquipmentVariant> allVariants = equipmentVariantRepository.findAll();
        Map<UUID, List<EquipmentVariant>> variantsByEquipment = allVariants.stream()
                .collect(Collectors.groupingBy(v -> v.getEquipment().getId()));

        return allEquipment.stream()
                .map(eq -> mapToResponse(eq, variantsByEquipment.getOrDefault(eq.getId(), List.of())))
                .toList();
    }

    @Override
    public List<EquipmentSelectionResponse> getEquipmentsByEnvironment(WorkoutEnvironment environment) {
        log.info("Fetching equipment for environment={}", environment);

        List<Equipment> allEquipment = equipmentRepository.findAll();
        List<EquipmentVariant> allVariants = equipmentVariantRepository.findAll();
        Map<UUID, List<EquipmentVariant>> variantsByEquipment = allVariants.stream()
                .collect(Collectors.groupingBy(v -> v.getEquipment().getId()));

        return allEquipment.stream()
                .map(eq -> {
                    boolean selected = isDefaultForEnvironment(eq, environment);
                    List<EquipmentVariant> variants = variantsByEquipment.getOrDefault(eq.getId(), List.of());
                    return mapToSelectionResponse(eq, variants, selected,
                            selected ? variants.stream().map(EquipmentVariant::getId).toList() : List.of());
                })
                .toList();
    }

    @Override
    public EquipmentResponse getEquipmentById(UUID id) {
        log.info("Fetching equipment with id={}", id);

        Equipment equipment = equipmentRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Equipment with id={} not found", id);
                    return new ResourceNotFoundException("Equipment not found");
                });

        List<EquipmentVariant> variants = equipmentVariantRepository
                .findByEquipmentIdOrderBySortOrder(id);

        return mapToResponse(equipment, variants);
    }

    @Override
    @Transactional
    public Equipment createEquipment(EquipmentRequest request) {
        log.info("Creating equipment: name={}", request.getName());

        Equipment equipment = new Equipment();
        equipment.setName(request.getName());
        equipment.setType(request.getType());
        equipment.setCategory(request.getCategory());
        equipment.setHasVariants(request.isHasVariants());

        Equipment saved = equipmentRepository.save(equipment);
        log.info("Equipment created with id={}", saved.getId());
        return saved;
    }

    @Override
    @Transactional
    public Equipment updateEquipment(UUID id, EquipmentRequest request) {
        log.info("Updating equipment with id={}", id);

        Equipment equipment = equipmentRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Equipment with id={} not found", id);
                    return new ResourceNotFoundException("Equipment not found");
                });

        equipment.setName(request.getName());
        equipment.setType(request.getType());
        equipment.setCategory(request.getCategory());
        equipment.setHasVariants(request.isHasVariants());

        Equipment saved = equipmentRepository.save(equipment);
        log.info("Equipment updated with id={}", saved.getId());
        return saved;
    }

    @Override
    @Transactional
    public void deleteEquipment(UUID id) {
        log.info("Deleting equipment with id={}", id);

        Equipment equipment = equipmentRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Equipment with id={} not found", id);
                    return new ResourceNotFoundException("Equipment not found");
                });

        equipmentRepository.delete(equipment);
        log.info("Equipment deleted with id={}", id);
    }

    @Override
    public List<UserEquipmentResponse> getUserEquipments(UUID userId) {
        log.info("Fetching user equipment for userId={}", userId);

        UserProfile profile = userProfileRepository.findByUser_Id(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Profile not found"));

        if (profile.getUserEquipments() == null || profile.getUserEquipments().isEmpty()) {
            return List.of();
        }

        Map<UUID, List<UserEquipment>> byEquipment = profile.getUserEquipments().stream()
                .collect(Collectors.groupingBy(ue -> ue.getEquipment().getId()));

        return byEquipment.entrySet().stream()
                .map(entry -> {
                    List<UserEquipment> ueList = entry.getValue();
                    UserEquipment first = ueList.get(0);
                    Equipment eq = first.getEquipment();

                    List<SelectedVariant> variants = ueList.stream()
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
                            .variants(variants)
                            .build();
                })
                .toList();
    }

    @Override
    @Transactional
    public void saveUserEquipments(UUID userId, EquipmentSelectionRequest request) {
        log.info("Saving equipment selections for userId={}", userId);

        UserProfile profile = userProfileRepository.findByUser_Id(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Profile not found. Complete profile first."));

        if (profile.getUserEquipments() != null && !profile.getUserEquipments().isEmpty()) {
            throw new ConflictException("Equipment selections already exist. Use PUT to update.");
        }

        replaceUserEquipment(profile, request);
        log.info("Equipment selections saved for userId={}", userId);
    }

    @Override
    @Transactional
    public void updateUserEquipments(UUID userId, EquipmentSelectionRequest request) {
        log.info("Updating equipment selections for userId={}", userId);

        UserProfile profile = userProfileRepository.findByUser_Id(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Profile not found. Complete profile first."));

        if (profile.getUserEquipments() == null || profile.getUserEquipments().isEmpty()) {
            throw new ResourceNotFoundException("No equipment selections found to update. Use POST to create.");
        }

        replaceUserEquipment(profile, request);
        log.info("Equipment selections updated for userId={}", userId);
    }

    @Override
    @Transactional
    public void deleteUserEquipments(UUID userId) {
        log.info("Deleting user equipment for userId={}", userId);

        UserProfile profile = userProfileRepository.findByUser_Id(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Profile not found"));

        if (profile.getUserEquipments() != null) {
            profile.getUserEquipments().clear();
            userProfileRepository.save(profile);
        }

        log.info("User equipment deleted for userId={}", userId);
    }

    private void replaceUserEquipment(UserProfile profile, EquipmentSelectionRequest request) {
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
    }

    private EquipmentResponse mapToResponse(Equipment eq, List<EquipmentVariant> variants) {
        List<EquipmentVariantResponse> variantResponses = variants.stream()
                .map(v -> EquipmentVariantResponse.builder()
                        .id(v.getId())
                        .weight(v.getWeight())
                        .weightUnit(v.getWeightUnit())
                        .build())
                .toList();

        return EquipmentResponse.builder()
                .id(eq.getId())
                .name(eq.getName())
                .type(eq.getType())
                .category(eq.getCategory())
                .hasVariants(eq.isHasVariants())
                .variants(variantResponses)
                .build();
    }

    private EquipmentSelectionResponse mapToSelectionResponse(Equipment eq, List<EquipmentVariant> variants,
            boolean selected, List<UUID> selectedVariantIds) {
        List<EquipmentVariantResponse> variantResponses = variants.stream()
                .map(v -> EquipmentVariantResponse.builder()
                        .id(v.getId())
                        .weight(v.getWeight())
                        .weightUnit(v.getWeightUnit())
                        .build())
                .toList();

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
