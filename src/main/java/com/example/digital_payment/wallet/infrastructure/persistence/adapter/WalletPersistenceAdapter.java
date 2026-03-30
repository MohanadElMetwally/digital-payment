package com.example.digital_payment.wallet.infrastructure.persistence.adapter;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.example.digital_payment.wallet.application.port.out.LoadWalletPort;
import com.example.digital_payment.wallet.application.port.out.SaveWalletPort;
import com.example.digital_payment.wallet.domain.model.entities.Wallets;
import com.example.digital_payment.wallet.infrastructure.persistence.entity.WalletEntity;
import com.example.digital_payment.wallet.infrastructure.persistence.mapper.WalletPersistenceMapper;
import com.example.digital_payment.wallet.infrastructure.persistence.repository.WalletJpaRepository;

@Component
public class WalletPersistenceAdapter implements SaveWalletPort, LoadWalletPort {
    private final WalletJpaRepository walletJpaRepository;
    private final WalletPersistenceMapper walletPersistenceMapper;

    public WalletPersistenceAdapter(WalletJpaRepository walletJpaRepository,
        WalletPersistenceMapper walletPersistenceMapper) {
        this.walletJpaRepository = walletJpaRepository;
        this.walletPersistenceMapper = walletPersistenceMapper;
    }

    public void save(Wallets wallet) {
        WalletEntity entity = walletPersistenceMapper.toEntity(wallet);
        walletJpaRepository.save(entity);
    }

    @Override
    public Optional<Wallets> getByUserId(UUID userId) {
        return walletJpaRepository.findByUserId(userId).map(walletPersistenceMapper::toDomain);
    }
}
