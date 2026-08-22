package com.example.digital_payment.settlement.infrastructure.persistence.mapper;

import org.springframework.stereotype.Component;
import com.example.digital_payment.settlement.domain.models.Settlements;
import com.example.digital_payment.settlement.domain.snapshots.SettlementSnapshot;
import com.example.digital_payment.settlement.infrastructure.persistence.entity.SettlementEntity;

@Component
public class SettlementPersistenceMapper {
    public SettlementEntity toEntity(Settlements settlement) {
        SettlementEntity entity = new SettlementEntity();
        entity.setId(settlement.getId());
        entity.setBillId(settlement.getBillId());
        entity.setUserId(settlement.getUserId());
        entity.setCustomerNumber(settlement.getCustomerNumber());
        entity.setAmount(settlement.getAmount());
        entity.setCurrency(settlement.getCurrency());
        entity.setStatus(settlement.getStatus());
        entity.setProviderIdempotencyKey(settlement.getProviderIdempotencyKey());
        entity.setProviderReference(settlement.getProviderReference());
        entity.setAttemptCount(settlement.getAttemptCount());
        entity.setLastAttemptedAt(settlement.getLastAttemptedAt());
        entity.setLastError(settlement.getLastError());
        entity.setProcessedAt(settlement.getProcessedAt());
        entity.setCreatedAt(settlement.getCreatedAt());
        return entity;
    }

    public Settlements toDomain(SettlementEntity entity) {
        SettlementSnapshot snapshot = new SettlementSnapshot(entity.getId(), entity.getBillId(),
                entity.getUserId(), entity.getCustomerNumber(), entity.getAmount(),
                entity.getCurrency(), entity.getStatus(), entity.getProviderIdempotencyKey(),
                entity.getProviderReference(), entity.getAttemptCount(),
                entity.getLastAttemptedAt(), entity.getLastError(), entity.getProcessedAt(),
                entity.getCreatedAt());
        return Settlements.reconstitute(snapshot);
    }

    public void update(Settlements settlement, SettlementEntity entity) {
        entity.setStatus(settlement.getStatus());
        entity.setAttemptCount(settlement.getAttemptCount());
        entity.setLastAttemptedAt(settlement.getLastAttemptedAt());
        entity.setLastError(settlement.getLastError());
        entity.setProcessedAt(settlement.getProcessedAt());
    }
}
