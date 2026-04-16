package com.example.digital_payment.billing.application.port.in;

import com.example.digital_payment.billing.domain.model.entities.Bills;

public interface ProviderUseCase {
    Bills fetchBill(String externalCustomerNumber);

    void payBill();
}
