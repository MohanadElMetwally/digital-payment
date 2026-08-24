package com.example.digital_payment.payment.domain.model.valueobjects;

import java.math.BigDecimal;
import java.util.UUID;
import com.example.digital_payment.payment.domain.enums.PaymentSourceType;

public record PaymentSourceCreationData(UUID transactionId, PaymentSourceType sourceType,
        UUID sourceId, BigDecimal amount) {
}
