package com.example.digital_payment.payment.infrastructure.persistence.mapper;

import org.springframework.stereotype.Component;
import com.example.digital_payment.payment.domain.model.entities.PaymentSources;
import com.example.digital_payment.payment.domain.model.snapshots.PaymentSourceSnapshot;
import com.example.digital_payment.payment.infrastructure.persistence.entity.PaymentSourceEntity;

@Component
public class PaymentSourcePersistenceMapper {
    public PaymentSourceEntity toEntity(PaymentSources paymentSource) {
        if (paymentSource == null)
            return null;
        PaymentSourceEntity entity = new PaymentSourceEntity();
        entity.setId(paymentSource.getId());
        entity.setTransactionId(paymentSource.getTransactionId());
        entity.setSourceType(paymentSource.getSourceType());
        entity.setSourceId(paymentSource.getSourceId());
        entity.setAmount(paymentSource.getAmount());
        entity.setCreatedAt(paymentSource.getCreatedAt());
        return entity;
    }

    public PaymentSources toDomain(PaymentSourceEntity entity) {
        if (entity == null)
            return null;
        PaymentSourceSnapshot snapshot = new PaymentSourceSnapshot(entity.getId(),
                entity.getTransactionId(), entity.getSourceType(), entity.getSourceId(),
                entity.getAmount(), entity.getCreatedAt());
        return PaymentSources.reconstitute(snapshot);
    }
}
