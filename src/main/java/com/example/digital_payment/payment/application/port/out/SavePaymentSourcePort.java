package com.example.digital_payment.payment.application.port.out;

import com.example.digital_payment.payment.domain.model.entities.PaymentSources;

public interface SavePaymentSourcePort {
    void save(PaymentSources paymentSource);
}
