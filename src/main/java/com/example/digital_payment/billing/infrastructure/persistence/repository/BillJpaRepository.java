package com.example.digital_payment.billing.infrastructure.persistence.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.digital_payment.billing.infrastructure.persistence.entity.BillEntity;

@Repository
public interface BillJpaRepository extends JpaRepository<BillEntity, UUID> {
    Optional<BillEntity> findByExternalBillId(String externalBillId);
    Optional<BillEntity> findByExternalBillIdAndBillerId(String externalBillId, UUID billerId);
}
