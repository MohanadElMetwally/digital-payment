package com.example.digital_payment.payment.application.port.out;

import java.util.Optional;
import java.util.UUID;

import com.example.digital_payment.payment.domain.model.entities.Transactions;

public interface IdempotencyStore {
    Optional<Transactions> get(UUID userId, UUID idempotencyKey);

    void save(UUID userId, UUID idempotencyKey, Transactions transaction);
}
