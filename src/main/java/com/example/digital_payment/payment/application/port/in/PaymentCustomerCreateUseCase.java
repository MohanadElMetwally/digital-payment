package com.example.digital_payment.payment.application.port.in;

import com.example.digital_payment.payment.application.dto.PaymentCustomerCreateCommand;

public interface PaymentCustomerCreateUseCase {
    void handle(PaymentCustomerCreateCommand command);
}
