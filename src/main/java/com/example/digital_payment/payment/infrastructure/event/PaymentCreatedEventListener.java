package com.example.digital_payment.payment.infrastructure.event;

import org.springframework.modulith.events.ApplicationModuleListener;
import org.springframework.stereotype.Component;

import com.example.digital_payment.payment.application.dto.PaymentCreatedCommand;
import com.example.digital_payment.payment.application.dto.PaymentInitiationCreatedEvent;
import com.example.digital_payment.payment.application.port.in.PaymentCreatedUseCase;

@Component
public class PaymentCreatedEventListener {
    private final PaymentCreatedUseCase paymentCreatedUseCase;

    public PaymentCreatedEventListener(PaymentCreatedUseCase paymentCreatedUseCase) {
        this.paymentCreatedUseCase = paymentCreatedUseCase;
    }

    @ApplicationModuleListener
    public void on(PaymentInitiationCreatedEvent event) {
        paymentCreatedUseCase
            .handle(new PaymentCreatedCommand(event.transactionId(), event.externalReference()));
    }
}
