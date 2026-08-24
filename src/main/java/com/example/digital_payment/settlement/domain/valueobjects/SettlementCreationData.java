package com.example.digital_payment.settlement.domain.valueobjects;

import java.math.BigDecimal;
import java.util.UUID;

public record SettlementCreationData(UUID billId, UUID userId, String customerNumber,
        BigDecimal amount, String currency) {
}
