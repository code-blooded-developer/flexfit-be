package com.harish.flexfit.repo;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.harish.flexfit.entity.EquipmentVariant;

public interface EquipmentVariantRepository extends JpaRepository<EquipmentVariant, UUID> {
    List<EquipmentVariant> findByEquipmentIdOrderBySortOrder(UUID equipmentId);
    List<EquipmentVariant> findByEquipmentIdIn(List<UUID> equipmentIds);
}
