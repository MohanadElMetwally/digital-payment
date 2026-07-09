package com.example.digital_payment.payment.application.usecase;

import java.util.UUID;

import com.example.digital_payment.payment.application.dto.PaymentFailedCommand;
import com.example.digital_payment.payment.application.port.in.PaymentFailedUseCase;
import com.example.digital_payment.payment.application.port.out.LoadBillPaymentByTransactionIdPort;
import com.example.digital_payment.payment.application.port.out.LoadTransactionPort;
import com.example.digital_payment.payment.application.port.out.UpdateBillPaymentPort;
import com.example.digital_payment.payment.application.port.out.UpdateTransactionPort;
import com.example.digital_payment.payment.domain.exceptions.BillPaymentNotFoundException;
import com.example.digital_payment.payment.domain.exceptions.TransactionNotFoundException;
import com.example.digital_payment.payment.domain.model.entities.BillPayments;
import com.example.digital_payment.payment.domain.model.entities.Transactions;
import com.example.digital_payment.shared.application.port.out.TransactionPort;

public class PaymentFailureHandler implements PaymentFailedUseCase {
    private final TransactionPort transactionPort;
    private final LoadTransactionPort loadTransactionPort;
    private final UpdateBillPaymentPort updateBillPaymentPort;
    private final UpdateTransactionPort updateTransactionPort;
    private final LoadBillPaymentByTransactionIdPort loadBillPaymentByTransactionIdPort;

    public PaymentFailureHandler(LoadTransactionPort loadTransactionPort,
        LoadBillPaymentByTransactionIdPort loadBillPaymentByTransactionIdPort,
        TransactionPort transactionPort, UpdateBillPaymentPort updateBillPaymentPort,
        UpdateTransactionPort updateTransactionPort) {
        this.transactionPort = transactionPort;
        this.loadTransactionPort = loadTransactionPort;
        this.updateTransactionPort = updateTransactionPort;
        this.updateBillPaymentPort = updateBillPaymentPort;
        this.loadBillPaymentByTransactionIdPort = loadBillPaymentByTransactionIdPort;
    }

    @Override
    public void handle(PaymentFailedCommand command) {
        transactionPort.executeVoid(() -> {
            Transactions tx = loadTransactionOrThrow(command.transactionId());
            BillPayments billPayment = loadBillPaymentOrThrow(command.transactionId());
            tx.markFailed(command.failureReason());
            billPayment.markFailed();
            updateTransactionPort.update(tx);
            updateBillPaymentPort.update(billPayment);
        });
    }

    private Transactions loadTransactionOrThrow(UUID transactionId) {
        return loadTransactionPort.findById(transactionId)
            .orElseThrow(() -> new TransactionNotFoundException("Transaction not found"));
    }

    private BillPayments loadBillPaymentOrThrow(UUID transactionId) {
        return loadBillPaymentByTransactionIdPort.findByTransactionId(transactionId)
            .orElseThrow(() -> new BillPaymentNotFoundException("Bill payment not found"));
    }

}
