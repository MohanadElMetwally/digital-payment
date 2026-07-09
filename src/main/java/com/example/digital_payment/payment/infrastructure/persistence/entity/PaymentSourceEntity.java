package com.example.digital_payment.payment.infrastructure.persistence.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import com.example.digital_payment.payment.domain.enums.PaymentSourceType;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "payment_sources")
public class PaymentSourceEntity {
    @Id
    private UUID id;
    private UUID transactionId;
    @Enumerated(EnumType.STRING)
    private PaymentSourceType sourceType;
    private UUID sourceId;
    private BigDecimal amount;
    private LocalDateTime createdAt;
}
