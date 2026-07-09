package com.example.digital_payment.payment.application.port.out;

import com.example.digital_payment.payment.domain.model.entities.Transactions;

public interface ProcessTransactionPort {
    void processTransaction(Transactions transaction);
}
