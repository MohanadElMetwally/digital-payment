package com.example.digital_payment.billing.application.usecase;

import com.example.digital_payment.billing.application.dto.ProcessBillPaymentCommand;
import com.example.digital_payment.billing.application.port.in.MarkBillPaymentSucceededUseCase;
import com.example.digital_payment.billing.application.port.out.LoadBillPaymentByTransactionIdPort;
import com.example.digital_payment.billing.application.port.out.LoadBillPort;
import com.example.digital_payment.billing.application.port.out.UpdateBillPaymentPort;
import com.example.digital_payment.billing.application.port.out.UpdateBillPort;
import com.example.digital_payment.billing.domain.exceptions.BillNotFoundException;
import com.example.digital_payment.billing.domain.exceptions.BillPaymentNotFoundException;
import com.example.digital_payment.billing.domain.model.entities.BillPayments;
import com.example.digital_payment.billing.domain.model.entities.Bills;
import com.example.digital_payment.shared.application.port.out.TransactionPort;

public class MarkBillPaymentSucceededService implements MarkBillPaymentSucceededUseCase {
    private final TransactionPort transactionPort;
    private final LoadBillPort loadBillPort;
    private final UpdateBillPort updateBillPort;
    private final LoadBillPaymentByTransactionIdPort loadBillPaymentPort;
    private final UpdateBillPaymentPort updateBillPaymentPort;

    public MarkBillPaymentSucceededService(TransactionPort transactionPort,
        LoadBillPort loadBillPort, UpdateBillPort updateBillPort,
        LoadBillPaymentByTransactionIdPort loadBillPaymentPort,
        UpdateBillPaymentPort updateBillPaymentPort) {
        this.transactionPort = transactionPort;
        this.loadBillPort = loadBillPort;
        this.updateBillPort = updateBillPort;
        this.loadBillPaymentPort = loadBillPaymentPort;
        this.updateBillPaymentPort = updateBillPaymentPort;
    }

    @Override
    public Bills mark(ProcessBillPaymentCommand command) {
        return transactionPort.execute(() -> {
            Bills bill = loadBillPort.findById(command.billId())
                .orElseThrow(() -> new BillNotFoundException());
            BillPayments billPayment = loadBillPaymentPort
                .findByTransactionId(command.transactionId())
                .orElseThrow(() -> new BillPaymentNotFoundException("BillPayment not found"));
            bill.markPaid();
            billPayment.markSucceeded();
            updateBillPort.update(bill);
            updateBillPaymentPort.update(billPayment);
            return bill;
        });
    }

}
