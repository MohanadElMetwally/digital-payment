package com.example.digital_payment.settlement.application.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record SaveSettlementCommand(UUID billId, UUID userId, String customerNumber,
        BigDecimal amount, String currency) {
}
