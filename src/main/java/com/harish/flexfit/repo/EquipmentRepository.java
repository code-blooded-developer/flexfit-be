package com.harish.flexfit.repo;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.harish.flexfit.entity.Equipment;

public interface EquipmentRepository extends JpaRepository<Equipment, UUID> {

}
