package com.example.digital_payment.settlement.infrastructure.persistence.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import com.example.digital_payment.settlement.domain.enums.FailedSettlementResolutionStatus;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "failed_settlements")
public class FailedSettlementEntity {
    @Id
    private UUID id;
    private UUID settlementId;
    private UUID billId;
    private UUID userId;
    private String customerNumber;
    private BigDecimal amount;
    private String currency;
    private String failureReason;
    private int attemptCount;
    @Enumerated(EnumType.STRING)
    private FailedSettlementResolutionStatus resolutionStatus;
    private String resolutionNotes;
    private String resolvedBy;
    private LocalDateTime resolvedAt;
    private LocalDateTime createdAt;
}