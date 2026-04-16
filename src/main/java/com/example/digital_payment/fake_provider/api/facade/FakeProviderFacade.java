package com.example.digital_payment.fake_provider.api.facade;

import org.springframework.stereotype.Component;

import com.example.digital_payment.fake_provider.api.dto.FakeBillResponse;
import com.example.digital_payment.fake_provider.api.mapper.FakeProviderApiMapper;
import com.example.digital_payment.fake_provider.application.dto.FakeBillResult;
import com.example.digital_payment.fake_provider.application.port.in.GetFakeBillUseCase;

@Component
public class FakeProviderFacade {
    private final GetFakeBillUseCase getFakeBillUseCase;
    private final FakeProviderApiMapper fakeProviderApiMapper;

    public FakeProviderFacade(GetFakeBillUseCase getFakeBillUseCase,
        FakeProviderApiMapper fakeProviderApiMapper) {
        this.getFakeBillUseCase = getFakeBillUseCase;
        this.fakeProviderApiMapper = fakeProviderApiMapper;
    }

    public FakeBillResponse getFakeBillByCustomerNumber(String customerNumber) {
        FakeBillResult result = getFakeBillUseCase.findByCustomerNumber(customerNumber);
        return fakeProviderApiMapper.toResponse(result);
    }
}
