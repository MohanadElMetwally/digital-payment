package com.example.digital_payment.fake_provider.application.usecase;

import com.example.digital_payment.fake_provider.application.port.in.MarkFakeBillPaidUseCase;
import com.example.digital_payment.fake_provider.application.port.out.LoadFakeBillPort;
import com.example.digital_payment.fake_provider.application.port.out.UpdateFakeBillPort;
import com.example.digital_payment.fake_provider.domain.exception.FakeBillNotFoundException;
import com.example.digital_payment.fake_provider.domain.model.entities.FakeBills;
import com.example.digital_payment.shared.application.port.out.TransactionPort;

public class MarkFakeBillPaidService implements MarkFakeBillPaidUseCase {
    private final TransactionPort transactionPort;
    private final LoadFakeBillPort loadFakeBillPort;
    private final UpdateFakeBillPort updateFakeBillPort;

    public MarkFakeBillPaidService(TransactionPort transactionPort,
            LoadFakeBillPort loadFakeBillPort, UpdateFakeBillPort updateFakeBillPort) {
        this.transactionPort = transactionPort;
        this.loadFakeBillPort = loadFakeBillPort;
        this.updateFakeBillPort = updateFakeBillPort;
    }

    @Override
    public void mark(String customerNumber) {
        transactionPort.executeVoid(() -> {
            FakeBills fakeBill = loadFakeBillPort.findByCustomerNumber(customerNumber)
                    .orElseThrow(() -> new FakeBillNotFoundException("bill not found"));
            fakeBill.markPaid();
            updateFakeBillPort.update(fakeBill);
        });
    }

}
