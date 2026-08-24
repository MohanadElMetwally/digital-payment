package com.example.digital_payment.payment.application.usecase;

import java.util.UUID;
import com.example.digital_payment.payment.application.dto.MarkPaymentSucceededCommand;
import com.example.digital_payment.payment.application.dto.MarkPaymentSucceededResult;
import com.example.digital_payment.payment.application.enums.MarkPaymentStatus;
import com.example.digital_payment.payment.application.port.in.MarkPaymentSucceededUseCase;
import com.example.digital_payment.payment.application.port.out.LoadTransactionPort;
import com.example.digital_payment.payment.application.port.out.UpdateTransactionPort;
import com.example.digital_payment.payment.domain.enums.TransactionStatus;
import com.example.digital_payment.payment.domain.exceptions.TransactionNotFoundException;
import com.example.digital_payment.payment.domain.model.entities.Transactions;
import com.example.digital_payment.shared.application.port.out.TransactionPort;

public class MarkPaymentSucceededHandler implements MarkPaymentSucceededUseCase {
    private final TransactionPort transactionPort;
    private final LoadTransactionPort loadTransactionPort;
    private final UpdateTransactionPort updateTransactionPort;

    public MarkPaymentSucceededHandler(LoadTransactionPort loadTransactionPort,
            TransactionPort transactionPort, UpdateTransactionPort updateTransactionPort) {
        this.transactionPort = transactionPort;
        this.loadTransactionPort = loadTransactionPort;
        this.updateTransactionPort = updateTransactionPort;
    }

    @Override
    public MarkPaymentSucceededResult handle(MarkPaymentSucceededCommand command) {
        return transactionPort.execute(() -> {
            Transactions tx = loadTransactionOrThrow(command.transactionId());
            if (tx.getStatus() != TransactionStatus.PENDING) {
                return new MarkPaymentSucceededResult(MarkPaymentStatus.PAYMENT_ALREADY_FINALIZED,
                        tx);
            }
            tx.markSucceeded();
            updateTransactionPort.update(tx);
            return new MarkPaymentSucceededResult(MarkPaymentStatus.PAYMENT_RECORDED, tx);
        });
    }

    private Transactions loadTransactionOrThrow(UUID transactionId) {
        return loadTransactionPort.findById(transactionId)
                .orElseThrow(() -> new TransactionNotFoundException("Transaction not found"));
    }
}
