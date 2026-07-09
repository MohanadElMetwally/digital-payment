package com.example.digital_payment.payment.infrastructure.event;

import org.springframework.modulith.events.ApplicationModuleListener;
import org.springframework.stereotype.Component;

import com.example.digital_payment.payment.application.dto.PaymentFailedCommand;
import com.example.digital_payment.payment.application.dto.PaymentInitiationFailedEvent;
import com.example.digital_payment.payment.application.port.in.PaymentFailedUseCase;

@Component
public class PaymentFailedEventListener {
    private final PaymentFailedUseCase paymentFailedUseCase;

    public PaymentFailedEventListener(PaymentFailedUseCase paymentFailedUseCase) {
        this.paymentFailedUseCase = paymentFailedUseCase;
    }

    @ApplicationModuleListener
    public void on(PaymentInitiationFailedEvent event) {
        paymentFailedUseCase
            .handle(new PaymentFailedCommand(event.transactionId(), event.failureReason()));
    }
}
