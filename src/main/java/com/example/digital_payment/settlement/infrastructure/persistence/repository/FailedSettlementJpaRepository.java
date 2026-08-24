package com.example.digital_payment.settlement.infrastructure.persistence.repository;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.digital_payment.settlement.infrastructure.persistence.entity.FailedSettlementEntity;

public interface FailedSettlementJpaRepository extends JpaRepository<FailedSettlementEntity, UUID> {
    Optional<FailedSettlementEntity> findBySettlementId(UUID settlementId);
}
