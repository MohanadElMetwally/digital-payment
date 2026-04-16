package com.example.digital_payment.fake_provider.api.mapper;

import org.springframework.stereotype.Component;

import com.example.digital_payment.fake_provider.api.dto.FakeBillResponse;
import com.example.digital_payment.fake_provider.application.dto.FakeBillResult;

@Component
public class FakeProviderApiMapper {
    public FakeBillResponse toResponse(FakeBillResult result) {
        return new FakeBillResponse(result.id(), result.customerNumber(), result.customerName(),
            result.amount(), result.currency(), result.provider().toString(),
            result.status().toString(), result.billingPeriodStart().toString(),
            result.billingPeriodEnd().toString(), result.dueDate().toString(),
            result.createdAt().toString());
    }
}
