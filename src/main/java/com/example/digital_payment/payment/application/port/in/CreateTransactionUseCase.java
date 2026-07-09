package com.example.digital_payment.payment.application.port.in;

import com.example.digital_payment.payment.domain.model.valueobjects.TransactionCreationData;

public interface CreateTransactionUseCase {
    void createTransaction(TransactionCreationData data);
}
