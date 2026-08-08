package com.example.digital_payment.fake_provider.infrastructure.persistence.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.digital_payment.fake_provider.infrastructure.persistence.entity.FakeBillEntity;

public interface FakeBillJpaRepository extends JpaRepository<FakeBillEntity, UUID> {
    Optional<FakeBillEntity> findByCustomerNumber(String customerNumber);
}
