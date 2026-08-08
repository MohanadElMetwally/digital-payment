package com.example.digital_payment.billing.application.port.out;

import com.example.digital_payment.billing.domain.model.entities.ProviderBills;

public interface ProviderGatewayPort {
    ProviderBills fetchBill(String externalCustomerNumber);
}
