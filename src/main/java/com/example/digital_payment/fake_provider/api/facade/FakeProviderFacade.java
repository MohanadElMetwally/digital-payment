package com.example.digital_payment.fake_provider.api.facade;

import org.springframework.stereotype.Component;
import com.example.digital_payment.fake_provider.api.dto.FakeBillResponse;
import com.example.digital_payment.fake_provider.api.mapper.FakeProviderApiMapper;
import com.example.digital_payment.fake_provider.application.dto.FakeBillResult;
import com.example.digital_payment.fake_provider.application.port.in.GetFakeBillUseCase;
import com.example.digital_payment.fake_provider.application.port.in.MarkFakeBillPaidUseCase;

@Component
public class FakeProviderFacade {
    private final GetFakeBillUseCase getFakeBillUseCase;
    private final FakeProviderApiMapper fakeProviderApiMapper;
    private final MarkFakeBillPaidUseCase markFakeBillPaidUseCase;

    public FakeProviderFacade(GetFakeBillUseCase getFakeBillUseCase,
            MarkFakeBillPaidUseCase markFakeBillPaidUseCase,
            FakeProviderApiMapper fakeProviderApiMapper) {
        this.getFakeBillUseCase = getFakeBillUseCase;
        this.markFakeBillPaidUseCase = markFakeBillPaidUseCase;
        this.fakeProviderApiMapper = fakeProviderApiMapper;
    }

    public FakeBillResponse getFakeBillByCustomerNumber(String customerNumber) {
        FakeBillResult result = getFakeBillUseCase.findByCustomerNumber(customerNumber);
        return fakeProviderApiMapper.toResponse(result);
    }

    public void markFakeBillPaid(String customerNumber) {
        markFakeBillPaidUseCase.mark(customerNumber);
    }
}
