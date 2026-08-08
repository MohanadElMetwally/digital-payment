package com.example.digital_payment.billing.application.usecase;

import com.example.digital_payment.billing.application.dto.ProcessBillPaymentCommand;
import com.example.digital_payment.billing.application.port.in.InitiateBillPaymentUseCase;
import com.example.digital_payment.billing.application.port.out.LoadBillPort;
import com.example.digital_payment.billing.application.port.out.SaveBillPaymentPort;
import com.example.digital_payment.billing.application.port.out.UpdateBillPort;
import com.example.digital_payment.billing.domain.exceptions.BillNotFoundException;
import com.example.digital_payment.billing.domain.model.entities.BillPayments;
import com.example.digital_payment.billing.domain.model.entities.Bills;
import com.example.digital_payment.billing.domain.model.valueobjects.BillPaymentCreationData;
import com.example.digital_payment.shared.application.port.out.TransactionPort;

public class InitiateBillPaymentService implements InitiateBillPaymentUseCase {
    private final TransactionPort transactionPort;
    private final LoadBillPort loadBillPort;
    private final UpdateBillPort updateBillPort;
    private final SaveBillPaymentPort saveBillPaymentPort;

    public InitiateBillPaymentService(TransactionPort transactionPort, LoadBillPort loadBillPort,
        UpdateBillPort updateBillPort, SaveBillPaymentPort saveBillPaymentPort) {
        this.transactionPort = transactionPort;
        this.loadBillPort = loadBillPort;
        this.updateBillPort = updateBillPort;
        this.saveBillPaymentPort = saveBillPaymentPort;
    }

    @Override
    public void initiate(ProcessBillPaymentCommand command) {
        transactionPort.executeVoid(() -> {
            Bills bill = loadBillPort.findById(command.billId())
                .orElseThrow(() -> new BillNotFoundException());
            BillPaymentCreationData creationData = new BillPaymentCreationData(
                command.transactionId(), command.billId(), bill.getAmount());
            BillPayments billPayment = BillPayments.create(creationData);
            bill.markPending();
            saveBillPaymentPort.save(billPayment);
            updateBillPort.update(bill);
        });
    }
}
