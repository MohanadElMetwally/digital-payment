package com.example.digital_payment.fake_provider.api.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record FakeBillResponse(UUID id, String customerNumber, String customerName,
        BigDecimal amount, String currency, String provider, String status,
        String billingPeriodStart, String billingPeriodEnd, String dueDate, String createdAt) {

}
