package com.example.digital_payment.fake_provider.application.mapper;

import com.example.digital_payment.fake_provider.application.dto.FakeBillResult;
import com.example.digital_payment.fake_provider.domain.model.entities.FakeBills;

public class FakeBillMapper {
    public FakeBillResult toResult(FakeBills fakeBill) {
        return new FakeBillResult(fakeBill.getId(), fakeBill.getCustomerNumber(),
            fakeBill.getCustomerName(), fakeBill.getAmount(), fakeBill.getCurrency(),
            fakeBill.getProvider(), fakeBill.getStatus(), fakeBill.getBillingPeriodStart(),
            fakeBill.getBillingPeriodEnd(), fakeBill.getDueDate(), fakeBill.getCreatedAt());
    }
}
