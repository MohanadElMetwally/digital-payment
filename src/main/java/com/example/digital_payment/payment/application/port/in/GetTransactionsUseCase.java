package com.example.digital_payment.payment.application.port.in;

import java.util.UUID;
import com.example.digital_payment.payment.domain.model.entities.Transactions;

public interface GetTransactionsUseCase {
    Transactions getTransaction(UUID userId);
}
