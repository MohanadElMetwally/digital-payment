package com.example.digital_payment.billing.infrastructure.persistence.mappers;

import com.example.digital_payment.billing.domain.model.entities.ProviderBills;
import com.example.digital_payment.billing.domain.model.snapshots.ProviderBillSnapshot;
import com.example.digital_payment.billing.infrastructure.dto.ProviderBillResponse;

public class ProviderBillMapper {
    public ProviderBills toDomain(ProviderBillResponse response) {
        ProviderBillSnapshot snapshot =
                new ProviderBillSnapshot(response.id(), response.customerNumber(),
                        response.customerName(), response.amount(), response.currency(),
                        response.provider(), response.status(), response.billingPeriodStart(),
                        response.billingPeriodEnd(), response.dueDate(), response.createAt());
        return ProviderBills.reconstitute(snapshot);
    }
}
