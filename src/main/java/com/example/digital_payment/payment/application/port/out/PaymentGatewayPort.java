package com.example.digital_payment.payment.application.port.out;

import com.example.digital_payment.payment.application.dto.CreatePaymentCommand;
import com.example.digital_payment.payment.application.dto.PaymentInitiationResult;

public interface PaymentGatewayPort {
    PaymentInitiationResult createPayment(CreatePaymentCommand command);
}
