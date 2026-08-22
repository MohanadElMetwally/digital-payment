package com.example.digital_payment.payment.infrastructure.persistence.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;
import com.example.digital_payment.payment.domain.enums.TransactionStatus;
import com.example.digital_payment.payment.domain.enums.TransactionType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "transactions")
public class TransactionEntity {
    @Id
    private UUID id;
    private UUID userId;
    private UUID idempotencyKey;
    private String referenceNumber;
    @Enumerated(EnumType.STRING)
    private TransactionType type;
    @Enumerated(EnumType.STRING)
    private TransactionStatus status;
    private BigDecimal amount;
    private String currency;
    private String externalReference;
    private String failureReason;
    private LocalDateTime createdAt;
    private LocalDateTime completedAt;
}
