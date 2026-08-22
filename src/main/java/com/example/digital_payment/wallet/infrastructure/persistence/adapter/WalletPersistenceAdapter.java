package com.example.digital_payment.wallet.infrastructure.persistence.adapter;

import java.util.Optional;
import java.util.UUID;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import com.example.digital_payment.wallet.application.port.out.LoadWalletPort;
import com.example.digital_payment.wallet.application.port.out.SaveWalletPort;
import com.example.digital_payment.wallet.application.port.out.UpdateWalletPort;
import com.example.digital_payment.wallet.domain.model.entities.Wallets;
import com.example.digital_payment.wallet.infrastructure.persistence.entity.WalletEntity;
import com.example.digital_payment.wallet.infrastructure.persistence.mapper.WalletPersistenceMapper;
import com.example.digital_payment.wallet.infrastructure.persistence.repository.WalletJpaRepository;

@Component
public class WalletPersistenceAdapter implements SaveWalletPort, LoadWalletPort, UpdateWalletPort {
    private final WalletJpaRepository walletJpaRepository;
    private final WalletPersistenceMapper walletPersistenceMapper;

    public WalletPersistenceAdapter(WalletJpaRepository walletJpaRepository,
            WalletPersistenceMapper walletPersistenceMapper) {
        this.walletJpaRepository = walletJpaRepository;
        this.walletPersistenceMapper = walletPersistenceMapper;
    }

    @Override
    @Transactional
    public void save(Wallets wallet) {
        WalletEntity entity = walletPersistenceMapper.toEntity(wallet);
        walletJpaRepository.save(entity);
    }

    @Override
    @Cacheable(value = "wallets", key = "#walletId")
    public Optional<Wallets> getById(UUID walletId) {
        return walletJpaRepository.findById(walletId).map(walletPersistenceMapper::toDomain);
    }

    @Override
    @Cacheable(value = "wallets", key = "#userId")
    public Optional<Wallets> getByUserId(UUID userId) {
        return walletJpaRepository.findByUserId(userId).map(walletPersistenceMapper::toDomain);
    }

    @Override
    @Transactional
    // @formatter:off
    @Caching(
        put = {
            @CachePut(value = "wallets", key = "#wallet.id"),
            @CachePut(value = "wallets", key = "#wallet.userId")
        }
    )
    // @formatter:on
    public Wallets update(Wallets wallet) {
        WalletEntity entity = walletJpaRepository.getReferenceById(wallet.getId());
        walletPersistenceMapper.update(wallet, entity);
        return wallet;
    }

}
