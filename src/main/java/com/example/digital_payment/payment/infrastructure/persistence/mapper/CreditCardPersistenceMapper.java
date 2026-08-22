package com.example.digital_payment.payment.infrastructure.persistence.mapper;

import org.springframework.stereotype.Component;
import com.example.digital_payment.payment.domain.model.entities.CreditCards;
import com.example.digital_payment.payment.domain.model.snapshots.CreditCardSnapshot;
import com.example.digital_payment.payment.infrastructure.persistence.entity.CreditCardEntity;

@Component
public class CreditCardPersistenceMapper {
    public CreditCardEntity toEntity(CreditCards card) {
        if (card == null)
            return null;
        CreditCardEntity entity = new CreditCardEntity();
        entity.setId(card.getId());
        entity.setUserId(card.getUserId());
        entity.setBrand(card.getBrand());
        entity.setLastFour(card.getLastFour());
        entity.setExpiryMonth(card.getExpiryMonth());
        entity.setExpiryYear(card.getExpiryYear());
        entity.setPaymentMethodId(card.getPaymentMethodId());
        entity.setStatus(card.getStatus());
        entity.setIsDefault(card.isDefault());
        entity.setCreatedAt(card.getCreatedAt());
        return entity;
    }

    public CreditCards toDomain(CreditCardEntity entity) {
        if (entity == null)
            return null;
        CreditCardSnapshot snapshot = new CreditCardSnapshot(entity.getId(), entity.getUserId(),
                entity.getBrand(), entity.getLastFour(), entity.getExpiryMonth(),
                entity.getExpiryYear(), entity.getPaymentMethodId(), entity.getStatus(),
                entity.getIsDefault(), entity.getCreatedAt());
        return CreditCards.reconstitute(snapshot);
    }
}
