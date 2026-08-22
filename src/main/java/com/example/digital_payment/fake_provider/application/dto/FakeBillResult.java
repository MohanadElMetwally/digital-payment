package com.example.digital_payment.fake_provider.application.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;
import com.example.digital_payment.fake_provider.domain.enums.Provider;
import com.example.digital_payment.fake_provider.domain.enums.ProviderBillStatus;

public record FakeBillResult(UUID id, String customerNumber, String customerName, BigDecimal amount,
        String currency, Provider provider, ProviderBillStatus status, LocalDate billingPeriodStart,
        LocalDate billingPeriodEnd, LocalDate dueDate, LocalDateTime createdAt) {

}
