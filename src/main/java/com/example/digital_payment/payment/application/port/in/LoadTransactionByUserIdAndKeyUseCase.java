package com.example.digital_payment.payment.application.port.in;

import java.util.Optional;
import java.util.UUID;
import com.example.digital_payment.payment.domain.model.entities.Transactions;

public interface LoadTransactionByUserIdAndKeyUseCase {
    Optional<Transactions> loadTransactionByUserIdAndKey(UUID userId, UUID idempotencyKey);
}
