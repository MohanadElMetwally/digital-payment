package com.example.digital_payment.payment.infrastructure.persistence.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.digital_payment.payment.infrastructure.persistence.entity.CreditCardEntity;

public interface CreditCardJpaRepository extends JpaRepository<CreditCardEntity, UUID> {
    List<CreditCardEntity> findByUserId(UUID userId);
}
