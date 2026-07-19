package com.example.digital_payment.payment.infrastructure.persistence.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.digital_payment.payment.infrastructure.persistence.entity.PaymentSourceEntity;

public interface PaymentSourceJpaRepository extends JpaRepository<PaymentSourceEntity, UUID> {
    List<PaymentSourceEntity> findByTransactionId(UUID transactionId);
}
