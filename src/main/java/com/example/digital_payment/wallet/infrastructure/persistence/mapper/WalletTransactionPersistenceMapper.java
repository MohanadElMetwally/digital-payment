package com.example.digital_payment.wallet.infrastructure.persistence.mapper;

import org.springframework.stereotype.Component;

import com.example.digital_payment.wallet.domain.model.entities.WalletTransactions;
import com.example.digital_payment.wallet.domain.model.snapshots.WalletTransactionSnapshot;
import com.example.digital_payment.wallet.infrastructure.persistence.entity.WalletTransactionEntity;

@Component
public class WalletTransactionPersistenceMapper {
    public WalletTransactionEntity toEntity(WalletTransactions wtx) {
        if (wtx == null)
            return null;

        WalletTransactionEntity entity = new WalletTransactionEntity();
        entity.setId(wtx.getId());
        entity.setWalletId(wtx.getWalletId());
        entity.setTransactionId(wtx.getTransactionId());
        entity.setType(wtx.getType());
        entity.setStatus(wtx.getStatus());
        entity.setBalanceAfter(wtx.getBalanceAfter());
        entity.setAmount(wtx.getAmount());
        entity.setCreatedAt(wtx.getCreatedAt());
        entity.setCompletedAt(wtx.getCompletedAt());
        return entity;
    }

    public WalletTransactions toDomain(WalletTransactionEntity entity) {
        if (entity == null)
            return null;

        WalletTransactionSnapshot snapshot = new WalletTransactionSnapshot(entity.getId(),
            entity.getWalletId(), entity.getTransactionId(), entity.getType(), entity.getStatus(),
            entity.getBalanceAfter(), entity.getAmount(), entity.getCreatedAt(),
            entity.getCompletedAt());
        return WalletTransactions.reconstitute(snapshot);
    }

    public WalletTransactionEntity update(WalletTransactions wtx, WalletTransactionEntity entity) {
        entity.setStatus(wtx.getStatus());
        entity.setCompletedAt(wtx.getCompletedAt());
        return entity;
    }
}