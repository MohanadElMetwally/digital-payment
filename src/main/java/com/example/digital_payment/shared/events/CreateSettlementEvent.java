package com.example.digital_payment.shared.events;

import java.math.BigDecimal;
import java.util.UUID;

public record CreateSettlementEvent(UUID billId, UUID userId, String customerNumber,
        BigDecimal amount, String currency) {

}
