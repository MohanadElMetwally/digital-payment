package com.example.digital_payment.billing.infrastructure.persistence.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.digital_payment.billing.infrastructure.persistence.entity.BillerEntity;

@Repository
public interface BillerJpaRepository extends JpaRepository<BillerEntity, UUID> {

}
