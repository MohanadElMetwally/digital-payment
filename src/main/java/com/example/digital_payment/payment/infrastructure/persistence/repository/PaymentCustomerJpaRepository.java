package com.example.digital_payment.payment.infrastructure.persistence.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.digital_payment.payment.infrastructure.persistence.entity.PaymentCustomerEntity;

public interface PaymentCustomerJpaRepository extends JpaRepository<PaymentCustomerEntity, UUID> {
    public Optional<PaymentCustomerEntity> findByUserId(UUID userId);
}
