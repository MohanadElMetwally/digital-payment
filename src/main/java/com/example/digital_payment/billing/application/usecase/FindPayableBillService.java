package com.example.digital_payment.billing.application.usecase;

import java.util.Optional;
import java.util.UUID;

import com.example.digital_payment.billing.application.port.out.FindPayableBillPort;
import com.example.digital_payment.shared.application.port.in.FindPayableBillUseCase;
import com.example.digital_payment.shared.dto.BillInfo;

public class FindPayableBillService implements FindPayableBillUseCase {
    private final FindPayableBillPort findPayableBillPort;

    public FindPayableBillService(FindPayableBillPort findPayableBillPort) {
        this.findPayableBillPort = findPayableBillPort;
    }

    @Override
    public Optional<BillInfo> fetchBillInfo(UUID id) {
        return findPayableBillPort.findPayableById(id);
    }

}
