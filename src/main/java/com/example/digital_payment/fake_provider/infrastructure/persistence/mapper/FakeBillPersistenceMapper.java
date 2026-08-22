package com.example.digital_payment.fake_provider.infrastructure.persistence.mapper;

import org.springframework.stereotype.Component;
import com.example.digital_payment.fake_provider.domain.model.entities.FakeBills;
import com.example.digital_payment.fake_provider.domain.model.snapshot.FakeBillSnapshot;
import com.example.digital_payment.fake_provider.infrastructure.persistence.entity.FakeBillEntity;

@Component
public class FakeBillPersistenceMapper {
    public FakeBills toDomain(FakeBillEntity entity) {
        FakeBillSnapshot snapshot = new FakeBillSnapshot(entity.getId(), entity.getCustomerNumber(),
                entity.getCustomerName(), entity.getAmount(), entity.getCurrency(),
                entity.getProvider(), entity.getStatus(), entity.getBillingPeriodStart(),
                entity.getBillingPeriodEnd(), entity.getDueDate(), entity.getCreatedAt());
        return FakeBills.reconstitute(snapshot);
    }

    public void update(FakeBills fakeBill, FakeBillEntity entity) {
        entity.setStatus(fakeBill.getStatus());
    }
}
