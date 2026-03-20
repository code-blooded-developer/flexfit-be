package com.harish.flexfit.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(
    name = "weight_logs",
    indexes = {
        @Index(name = "idex_weight_logs_user_id", columnList = "user_id")
    }
)
public class WeightLog {

    @Id
    @GeneratedValue
    @Column(columnDefinition = "uuid", updatable = false)
    private UUID id;

    @Column(name = "user_id", nullable = false)
    private UUID userId;

    @Column(nullable = false)
    private Double weight;

    @Column(nullable = false)
    private Double weightInKg;

    @CreationTimestamp
    private LocalDateTime recordedAt;
}
