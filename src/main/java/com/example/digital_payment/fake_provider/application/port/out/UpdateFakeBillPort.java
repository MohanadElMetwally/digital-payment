package com.example.digital_payment.fake_provider.application.port.out;

import com.example.digital_payment.fake_provider.domain.model.entities.FakeBills;

public interface UpdateFakeBillPort {
    void update(FakeBills fakeBill);
}
