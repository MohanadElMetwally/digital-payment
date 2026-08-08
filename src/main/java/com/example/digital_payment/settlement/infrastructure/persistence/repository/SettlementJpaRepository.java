package com.example.digital_payment.settlement.infrastructure.persistence.repository;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.digital_payment.settlement.infrastructure.persistence.entity.SettlementEntity;

public interface SettlementJpaRepository extends JpaRepository<SettlementEntity, UUID> {
    @Modifying
    @Query("""
        update SettlementEntity s
        set s.status = 'PROCESSING',
            s.attemptCount = s.attemptCount + 1,
            s.lastAttemptedAt = :dt
        where s.id = :id and s.status = 'QUEUED'
        """)
    int claimForProcessing(@Param("id") UUID id, @Param("dt") LocalDateTime dt);

    @Modifying
    @Query(value = """
        WITH claimed AS (
            UPDATE settlements
            SET status = 'QUEUED'
            WHERE id IN (
                SELECT id FROM settlements
                WHERE status = 'PENDING'
                ORDER BY id
                LIMIT :batchSize
                FOR UPDATE SKIP LOCKED
            )
            RETURNING id
        )
        INSERT INTO settlements_outbox (id, settlement_id, status, attempt_count, created_at)
        SELECT gen_random_uuid(), id, 'PENDING', 0, :dt
        FROM claimed
        """, nativeQuery = true)
    int claimAndEnqueueBatch(@Param("dt") LocalDateTime dt, @Param("batchSize") int batchSize);
}