package com.example.digital_payment.settlement.infrastructure.persistence.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.example.digital_payment.settlement.infrastructure.persistence.entity.SettlementOutboxEntity;

public interface SettlementOutboxJpaRepository extends JpaRepository<SettlementOutboxEntity, UUID> {
    Optional<SettlementOutboxEntity> findBySettlementId(UUID settlementId);

    @Query(value = """
            SELECT * FROM settlements_outbox
            WHERE status = 'PENDING'
            ORDER BY created_at
            LIMIT :batchSize
            FOR UPDATE SKIP LOCKED
            """, nativeQuery = true)
    List<SettlementOutboxEntity> lockPendingBatch(@Param("batchSize") int batchSize);
}
