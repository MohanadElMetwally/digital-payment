package com.example.digital_payment.payment.application.port.out;

import java.util.List;
import java.util.UUID;
import com.example.digital_payment.payment.domain.model.entities.Transactions;

public interface LoadTransactionsPort {
    List<Transactions> loadTransactions(UUID userId);
}
