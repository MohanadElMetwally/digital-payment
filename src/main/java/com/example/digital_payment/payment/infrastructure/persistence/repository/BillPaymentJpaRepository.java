package com.example.digital_payment.payment.infrastructure.persistence.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.digital_payment.payment.infrastructure.persistence.entity.BillPaymentEntity;

public interface BillPaymentJpaRepository extends JpaRepository<BillPaymentEntity, UUID> {
    Optional<BillPaymentEntity> findByTransactionId(UUID transactionId);
}
