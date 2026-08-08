package com.example.digital_payment.billing.application.usecase;

import java.util.UUID;

import com.example.digital_payment.billing.application.port.out.LoadBillPort;
import com.example.digital_payment.billing.domain.model.entities.Bills;
import com.example.digital_payment.shared.application.port.in.FindBillInfoUseCase;
import com.example.digital_payment.shared.dto.BillInfo;
import com.example.digital_payment.shared.exception.ResourceNotFoundException;

public class FindBillInfoService implements FindBillInfoUseCase {
    private final LoadBillPort loadBillPort;

    public FindBillInfoService(LoadBillPort loadBillPort) {
        this.loadBillPort = loadBillPort;
    }

    @Override
    public BillInfo fetchBillInfo(UUID id) {
        Bills bill = loadBillPort.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Bill not found"));
        return new BillInfo(bill.getCurrency(), bill.getAmount(), bill.getExternalCustomerNumber());
    }

}
