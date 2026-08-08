package com.example.digital_payment.settlement.infrastructure.persistence.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import com.example.digital_payment.settlement.domain.enums.SettlementStatus;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "settlements")
public class SettlementEntity {
    @Id
    private UUID id;
    private UUID billId;
    private UUID userId;
    private String customerNumber;
    private BigDecimal amount;
    private String currency;
    @Enumerated(EnumType.STRING)
    private SettlementStatus status;
    private UUID providerIdempotencyKey;
    private String providerReference;
    private int attemptCount;
    private LocalDateTime lastAttemptedAt;
    private String lastError;
    private LocalDateTime processedAt;
    private LocalDateTime createdAt;
}