package com.example.digital_payment.payment.infrastructure.persistence.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.digital_payment.payment.infrastructure.persistence.entity.TransactionEntity;

public interface TransactionJpaRepository extends JpaRepository<TransactionEntity, UUID> {
    List<TransactionEntity> findByUserId(UUID userId);

    Optional<TransactionEntity> findByUserIdAndIdempotencyKey(UUID userId, UUID idempotencyKey);
}
