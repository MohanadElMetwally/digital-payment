package com.example.digital_payment.fake_provider.application.usecase;

import com.example.digital_payment.fake_provider.application.dto.FakeBillResult;
import com.example.digital_payment.fake_provider.application.mapper.FakeBillMapper;
import com.example.digital_payment.fake_provider.application.port.in.GetFakeBillUseCase;
import com.example.digital_payment.fake_provider.application.port.out.LoadFakeBillPort;
import com.example.digital_payment.fake_provider.domain.enums.ProviderBillStatus;
import com.example.digital_payment.fake_provider.domain.exception.FakeBillAlreadyPaidException;
import com.example.digital_payment.fake_provider.domain.exception.FakeBillNotFoundException;
import com.example.digital_payment.fake_provider.domain.model.entities.FakeBills;

public class GetFakeBillService implements GetFakeBillUseCase {
    private final LoadFakeBillPort loadFakeBillPort;
    private final FakeBillMapper fakeBillMapper;

    public GetFakeBillService(LoadFakeBillPort loadFakeBillPort, FakeBillMapper fakeBillMapper) {
        this.loadFakeBillPort = loadFakeBillPort;
        this.fakeBillMapper = fakeBillMapper;
    }

    @Override
    public FakeBillResult findByCustomerNumber(String customerNumber) {
        FakeBills fakeBill = loadFakeBillPort.findByCustomerNumber(customerNumber)
            .orElseThrow(
                () -> new FakeBillNotFoundException("Fake Bill not found: " + customerNumber));
        if (fakeBill.getStatus() == ProviderBillStatus.PAID) {
            throw new FakeBillAlreadyPaidException("Bill has already been paid");
        }

        return fakeBillMapper.toResult(fakeBill);
    }

}
