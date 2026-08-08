package com.example.digital_payment.payment.application.usecase;

import java.util.UUID;

import com.example.digital_payment.payment.application.dto.MarkPaymentFailedCommand;
import com.example.digital_payment.payment.application.dto.MarkPaymentFailedResult;
import com.example.digital_payment.payment.application.enums.MarkPaymentStatus;
import com.example.digital_payment.payment.application.port.in.MarkPaymentFailedUseCase;
import com.example.digital_payment.payment.application.port.out.LoadTransactionPort;
import com.example.digital_payment.payment.application.port.out.UpdateTransactionPort;
import com.example.digital_payment.payment.domain.enums.TransactionStatus;
import com.example.digital_payment.payment.domain.exceptions.TransactionNotFoundException;
import com.example.digital_payment.payment.domain.model.entities.Transactions;
import com.example.digital_payment.shared.application.port.out.TransactionPort;

public class MarkPaymentFailedHandler implements MarkPaymentFailedUseCase {
    private final TransactionPort transactionPort;
    private final LoadTransactionPort loadTransactionPort;
    private final UpdateTransactionPort updateTransactionPort;

    public MarkPaymentFailedHandler(LoadTransactionPort loadTransactionPort,
        TransactionPort transactionPort, UpdateTransactionPort updateTransactionPort) {
        this.transactionPort = transactionPort;
        this.loadTransactionPort = loadTransactionPort;
        this.updateTransactionPort = updateTransactionPort;
    }

    @Override
    public MarkPaymentFailedResult handle(MarkPaymentFailedCommand command) {
        return transactionPort.execute(() -> {
            Transactions tx = loadTransactionOrThrow(command.transactionId());
            if (tx.getStatus() != TransactionStatus.PENDING) {
                return new MarkPaymentFailedResult(MarkPaymentStatus.PAYMENT_ALREADY_FINALIZED, tx);
            }
            tx.markFailed(command.failureReason());
            updateTransactionPort.update(tx);
            return new MarkPaymentFailedResult(MarkPaymentStatus.PAYMENT_RECORDED, tx);
        });
    }

    private Transactions loadTransactionOrThrow(UUID transactionId) {
        return loadTransactionPort.findById(transactionId)
            .orElseThrow(() -> new TransactionNotFoundException("Transaction not found"));
    }

}
