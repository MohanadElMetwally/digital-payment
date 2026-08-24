package com.example.digital_payment.settlement.infrastructure.persistence.entity;

import java.time.LocalDateTime;
import java.util.UUID;
import com.example.digital_payment.settlement.domain.enums.OutboxStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "settlements_outbox")
public class SettlementOutboxEntity {
    @Id
    private UUID id;
    private UUID settlementId;
    @Enumerated(EnumType.STRING)
    private OutboxStatus status;
    private int attemptCount;
    private String lastError;
    private LocalDateTime createdAt;
    private LocalDateTime publishedAt;
}
