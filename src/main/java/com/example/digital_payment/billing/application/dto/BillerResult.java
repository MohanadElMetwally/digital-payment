package com.example.digital_payment.billing.application.dto;

import java.time.LocalDateTime;
import java.util.UUID;
import com.example.digital_payment.billing.domain.enums.BillCategory;
import com.example.digital_payment.billing.domain.enums.ServiceProvider;

public record BillerResult(UUID id, String name, BillCategory category,
        ServiceProvider serviceProvider, LocalDateTime createdAt) {

}
