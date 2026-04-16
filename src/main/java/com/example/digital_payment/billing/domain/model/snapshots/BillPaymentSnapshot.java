package com.example.digital_payment.billing.domain.model.snapshots;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import com.example.digital_payment.billing.domain.enums.BillPaymentStatus;

public record BillPaymentSnapshot(UUID id, UUID transaction_id, UUID bill_id, BigDecimal amount,
    BillPaymentStatus status, LocalDateTime paid_at) {

}
