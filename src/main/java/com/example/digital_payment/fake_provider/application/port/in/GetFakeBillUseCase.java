package com.example.digital_payment.fake_provider.application.port.in;

import com.example.digital_payment.fake_provider.application.dto.FakeBillResult;

public interface GetFakeBillUseCase {
    FakeBillResult findByCustomerNumber(String customerNumber);
}
