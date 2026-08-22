package com.example.digital_payment.payment.application.usecase;

import java.util.Optional;
import java.util.UUID;
import com.example.digital_payment.payment.application.port.in.LoadTransactionByUserIdAndKeyUseCase;
import com.example.digital_payment.payment.application.port.out.LoadTransactionByUserIdAndKeyPort;
import com.example.digital_payment.payment.domain.model.entities.Transactions;

public class LoadTransactionByUserIdAndKeyService implements LoadTransactionByUserIdAndKeyUseCase {
    private final LoadTransactionByUserIdAndKeyPort loadTransactionByUserIdAndKeyPort;

    public LoadTransactionByUserIdAndKeyService(
            LoadTransactionByUserIdAndKeyPort loadTransactionByUserIdAndKeyPort) {
        this.loadTransactionByUserIdAndKeyPort = loadTransactionByUserIdAndKeyPort;
    }

    @Override
    public Optional<Transactions> loadTransactionByUserIdAndKey(UUID userId, UUID idempotencyKey) {
        return loadTransactionByUserIdAndKeyPort.findByUserIdAndKey(userId, idempotencyKey);
    }

}
