package com.example.digital_payment.payment.domain.model.snapshots;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import com.example.digital_payment.payment.domain.enums.TransactionStatus;

public record BillPaymentSnapshot(UUID id, UUID transactionId, UUID billId, BigDecimal amount,
    TransactionStatus status, LocalDateTime paidAt) {

}
