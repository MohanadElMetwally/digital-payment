package com.example.digital_payment.billing.api.mapper;

import org.springframework.stereotype.Component;

import com.example.digital_payment.billing.api.dto.response.BillResponse;
import com.example.digital_payment.billing.application.dto.BillResult;

@Component
public class BillApiMapper {
    public BillResponse toResponse(BillResult result) {
        return new BillResponse(result.id(), result.billerId(), result.userId(),
            result.externalCustomerNumber(), result.externalCustomerName(), result.externalBillId(),
            result.amount(), result.currency(), result.status().toString(),
            result.billingPeriodStart().toString(), result.billingPeriodEnd().toString(),
            result.dueDate().toString(), result.lastSyncedAt().toString(),
            result.createdAt().toString());
    }
}
