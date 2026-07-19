package com.example.digital_payment.wallet.infrastructure.persistence.mapper;

import org.springframework.stereotype.Component;

import com.example.digital_payment.wallet.domain.model.entities.Wallets;
import com.example.digital_payment.wallet.domain.model.snapshots.WalletSnapshot;
import com.example.digital_payment.wallet.infrastructure.persistence.entity.WalletEntity;

@Component
public class WalletPersistenceMapper {
    public WalletEntity toEntity(Wallets wallet) {
        WalletEntity entity = new WalletEntity();
        entity.setId(wallet.getId());
        entity.setUserId(wallet.getUserId());
        entity.setCurrency(wallet.getCurrency());
        entity.setBalance(wallet.getBalance());
        entity.setCreatedAt(wallet.getCreatedAt());
        return entity;
    }

    public Wallets toDomain(WalletEntity entity) {
        WalletSnapshot snapshot = new WalletSnapshot(entity.getId(), entity.getUserId(),
            entity.getBalance(), entity.getCurrency(), entity.getCreatedAt());
        return Wallets.reconstitute(snapshot);
    }

    public void update(Wallets wallet, WalletEntity entity) {
        entity.setBalance(wallet.getBalance());
    }
}
