package com.example.digital_payment.payment.application.usecase;

import com.example.digital_payment.payment.application.dto.PaymentCreatedCommand;
import com.example.digital_payment.payment.application.port.in.PaymentCreatedUseCase;
import com.example.digital_payment.payment.application.port.out.LoadTransactionPort;
import com.example.digital_payment.payment.application.port.out.UpdateTransactionPort;
import com.example.digital_payment.payment.domain.exceptions.TransactionNotFoundException;
import com.example.digital_payment.payment.domain.model.entities.Transactions;
import com.example.digital_payment.shared.application.port.out.TransactionPort;

public class PaymentCreationHandler implements PaymentCreatedUseCase {
    private final TransactionPort transactionPort;
    private final LoadTransactionPort loadTransactionPort;
    private final UpdateTransactionPort updateTransactionPort;

    public PaymentCreationHandler(TransactionPort transactionPort,
        LoadTransactionPort loadTransactionPort, UpdateTransactionPort updateTransactionPort) {
        this.transactionPort = transactionPort;
        this.loadTransactionPort = loadTransactionPort;
        this.updateTransactionPort = updateTransactionPort;
    }

    @Override
    public void handle(PaymentCreatedCommand command) {
        transactionPort.executeVoid(() -> {
            Transactions tx = loadTransactionPort.findById(command.transactionId())
                .orElseThrow(() -> new TransactionNotFoundException("Transaction not found"));
            tx.markCreated(command.externalReference());            
            updateTransactionPort.update(tx);
        });
    }

}
