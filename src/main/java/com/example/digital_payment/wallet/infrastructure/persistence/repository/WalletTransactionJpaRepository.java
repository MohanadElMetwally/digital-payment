package com.example.digital_payment.wallet.infrastructure.persistence.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.digital_payment.wallet.infrastructure.persistence.entity.WalletTransactionEntity;

public interface WalletTransactionJpaRepository
    extends JpaRepository<WalletTransactionEntity, UUID> {
    List<WalletTransactionEntity> findByWalletId(UUID walletId);

    Optional<WalletTransactionEntity> findByTransactionId(UUID transactionId);
}