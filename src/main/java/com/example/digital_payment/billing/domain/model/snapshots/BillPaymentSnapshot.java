package com.example.digital_payment.billing.domain.model.snapshots;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;
import com.example.digital_payment.billing.domain.enums.BillPaymentStatus;

public record BillPaymentSnapshot(UUID id, UUID transactionId, UUID billId, BigDecimal amount,
        BillPaymentStatus status, LocalDateTime paidAt) {

}
