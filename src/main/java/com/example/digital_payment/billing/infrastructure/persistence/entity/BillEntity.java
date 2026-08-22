package com.example.digital_payment.billing.infrastructure.persistence.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;
import com.example.digital_payment.billing.domain.enums.BillStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "Bills")
public class BillEntity {
    @Id
    private UUID id;
    private UUID billerId;
    private UUID userId;
    private String externalCustomerNumber;
    private String externalCustomerName;
    private String externalBillId;
    private BigDecimal amount;
    private String currency;
    @Enumerated(EnumType.STRING)
    private BillStatus status;
    private LocalDate billingPeriodStart;
    private LocalDate billingPeriodEnd;
    private LocalDate dueDate;
    private LocalDateTime lastSyncedAt;
    private LocalDateTime createdAt;
}
