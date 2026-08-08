package com.example.digital_payment.fake_provider.infrastructure.persistence.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import com.example.digital_payment.fake_provider.domain.enums.Provider;
import com.example.digital_payment.fake_provider.domain.enums.ProviderBillStatus;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "fake_bills")
public class FakeBillEntity {
    @Id
    private UUID id;
    private String customerNumber;
    private String customerName;
    private BigDecimal amount;
    private String currency;
    @Enumerated(EnumType.STRING)
    private Provider provider;
    @Enumerated(EnumType.STRING)
    private ProviderBillStatus status;
    private LocalDate billingPeriodStart;
    private LocalDate billingPeriodEnd;
    private LocalDate dueDate;
    private LocalDateTime createdAt;
}
