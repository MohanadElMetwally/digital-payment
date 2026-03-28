package com.example.digital_payment.wallet.infrastructure.persistence.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.digital_payment.wallet.infrastructure.persistence.entity.WalletEntity;

public interface WalletJpaRepository extends JpaRepository<WalletEntity, UUID> {

}
