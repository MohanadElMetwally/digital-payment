package com.example.digital_payment.settlement.infrastructure.persistence.mapper;

import org.springframework.stereotype.Component;

import com.example.digital_payment.settlement.domain.models.SettlementsOutbox;
import com.example.digital_payment.settlement.domain.snapshots.SettlementOutboxSnapshot;
import com.example.digital_payment.settlement.infrastructure.persistence.entity.SettlementOutboxEntity;

@Component
public class SettlementOutboxPersistenceMapper {
    public SettlementOutboxEntity toEntity(SettlementsOutbox outbox) {
        SettlementOutboxEntity entity = new SettlementOutboxEntity();
        entity.setId(outbox.getId());
        entity.setSettlementId(outbox.getSettlementId());
        entity.setStatus(outbox.getStatus());
        entity.setAttemptCount(outbox.getAttemptCount());
        entity.setLastError(outbox.getLastError());
        entity.setCreatedAt(outbox.getCreatedAt());
        entity.setPublishedAt(outbox.getPublishedAt());
        return entity;
    }

    public SettlementsOutbox toDomain(SettlementOutboxEntity entity) {
        SettlementOutboxSnapshot snapshot = new SettlementOutboxSnapshot(entity.getId(),
            entity.getSettlementId(), entity.getStatus(), entity.getAttemptCount(),
            entity.getLastError(), entity.getCreatedAt(), entity.getPublishedAt());
        return SettlementsOutbox.reconstitute(snapshot);
    }

    public void update(SettlementsOutbox outbox, SettlementOutboxEntity entity) {
        entity.setStatus(outbox.getStatus());
        entity.setAttemptCount(outbox.getAttemptCount());
        entity.setLastError(outbox.getLastError());
        entity.setPublishedAt(outbox.getPublishedAt());
    }
}