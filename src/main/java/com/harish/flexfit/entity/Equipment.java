package com.harish.flexfit.entity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.harish.flexfit.enums.EquipmentType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(
    name = "equipment",
    indexes = {
        @Index(name = "idx_equipment_name", columnList = "name")
    }
)
public class Equipment {
    @Id
    @GeneratedValue
    @Column(columnDefinition = "uuid", updatable = false)
    private UUID id;

    @Column(nullable = false, unique = true)
    private String name;

    @Enumerated(EnumType.STRING)
    private EquipmentType type;

    @ManyToMany(mappedBy = "equipments")
    private List<UserProfile> userProfiles;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
