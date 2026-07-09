package com.example.digital_payment.payment.application.port.out;

import com.example.digital_payment.payment.application.dto.PaymentCustomerCreateCommand;

public interface PaymentCustomerGatewayPort {
    String createCustomer(PaymentCustomerCreateCommand command);
}
