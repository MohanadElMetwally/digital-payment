package com.example.digital_payment.fake_provider.application.port.out;

import java.util.Optional;

import com.example.digital_payment.fake_provider.domain.model.entities.FakeBills;

public interface LoadFakeBillPort {
    Optional<FakeBills> findByCustomerNumber(String customerNumber);
}
