package com.example.digital_payment.payment.infrastructure.persistence.mapper;

import org.springframework.stereotype.Component;

import com.example.digital_payment.payment.domain.model.entities.Transactions;
import com.example.digital_payment.payment.domain.model.snapshots.TransactionSnapshot;
import com.example.digital_payment.payment.infrastructure.persistence.entity.TransactionEntity;

@Component
public class TransactionPersistenceMapper {
    public TransactionEntity toEntity(Transactions tx) {
        if (tx == null)
            return null;
        TransactionEntity entity = new TransactionEntity();
        entity.setId(tx.getId());
        entity.setUserId(tx.getUserId());
        entity.setIdempotencyKey(tx.getIdempotencyKey());
        entity.setReferenceNumber(tx.getReferenceNumber());
        entity.setType(tx.getType());
        entity.setStatus(tx.getStatus());
        entity.setAmount(tx.getAmount());
        entity.setCurrency(tx.getCurrency());
        entity.setExternalReference(tx.getExternalReference());
        entity.setFailureReason(tx.getFailureReason());
        entity.setCreatedAt(tx.getCreatedAt());
        entity.setCompletedAt(tx.getCompletedAt());
        return entity;
    }

    public Transactions toDomain(TransactionEntity entity) {
        if (entity == null)
            return null;
        TransactionSnapshot snapshot = new TransactionSnapshot(entity.getId(), entity.getUserId(),
            entity.getIdempotencyKey(), entity.getReferenceNumber(), entity.getType(),
            entity.getStatus(), entity.getAmount(), entity.getCurrency(),
            entity.getExternalReference(), entity.getFailureReason(), entity.getCreatedAt(),
            entity.getCompletedAt());
        return Transactions.reconstitute(snapshot);
    }

    public TransactionEntity updateEntity(Transactions transaction, TransactionEntity entity) {
        entity.setExternalReference(transaction.getExternalReference());
        entity.setFailureReason(transaction.getFailureReason());
        entity.setCompletedAt(transaction.getCompletedAt());
        entity.setStatus(transaction.getStatus());
        return entity;
    }
}
