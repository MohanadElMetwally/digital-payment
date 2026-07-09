package com.example.digital_payment.payment.application.port.in;

import com.example.digital_payment.payment.application.dto.PaymentCreatedCommand;

public interface PaymentCreatedUseCase {
    void handle(PaymentCreatedCommand command);
}
