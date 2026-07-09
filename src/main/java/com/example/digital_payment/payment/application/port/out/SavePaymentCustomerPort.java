package com.example.digital_payment.payment.application.port.out;

import com.example.digital_payment.payment.domain.model.entities.PaymentCustomers;

public interface SavePaymentCustomerPort {
    PaymentCustomers save(PaymentCustomers customer);
}
