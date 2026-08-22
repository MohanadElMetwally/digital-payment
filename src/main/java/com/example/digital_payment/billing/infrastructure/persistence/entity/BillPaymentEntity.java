package com.example.digital_payment.billing.infrastructure.persistence.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;
import com.example.digital_payment.billing.domain.enums.BillPaymentStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "bill_payments")
public class BillPaymentEntity {
    @Id
    private UUID id;
    private UUID transactionId;
    private UUID billId;
    private BigDecimal amount;
    @Enumerated(EnumType.STRING)
    private BillPaymentStatus status;
    private LocalDateTime paidAt;
}
