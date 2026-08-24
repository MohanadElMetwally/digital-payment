package com.example.digital_payment.payment.application.dto;

import java.util.UUID;
import com.example.digital_payment.payment.domain.enums.TransactionStatus;

public record PaymentNotificationPayload(UUID transactionId, TransactionStatus status,
        String failureReason) {
    public PaymentNotificationPayload(UUID transactionId, TransactionStatus status) {
        this(transactionId, status, null);
    }
}
