package com.example.digital_payment.payment.application.port.out;

public interface RegisterCardGateway {
    void register(String customerId, String paymentMethodId);
}
