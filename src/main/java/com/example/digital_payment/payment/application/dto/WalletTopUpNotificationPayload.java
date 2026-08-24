package com.example.digital_payment.payment.application.dto;

import java.math.BigDecimal;
import java.util.UUID;
import com.example.digital_payment.payment.domain.enums.TransactionStatus;

public record WalletTopUpNotificationPayload(UUID transactionId, TransactionStatus status,
        BigDecimal amount) {

}
