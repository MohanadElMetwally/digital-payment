package com.example.digital_payment.payment.application.port.in;

import com.example.digital_payment.payment.application.dto.PaymentFailedCommand;

public interface PaymentFailedUseCase {
    void handle(PaymentFailedCommand command);
}
