package com.example.digital_payment.payment.infrastructure.event;

import org.springframework.modulith.events.ApplicationModuleListener;
import org.springframework.stereotype.Component;
import com.example.digital_payment.payment.application.dto.MarkPaymentFailedCommand;
import com.example.digital_payment.payment.application.dto.PaymentInitiationFailedEvent;
import com.example.digital_payment.payment.application.port.in.MarkPaymentFailedUseCase;

@Component
public class PaymentInitiationFailedEventListener {
    private final MarkPaymentFailedUseCase paymentFailedUseCase;

    public PaymentInitiationFailedEventListener(MarkPaymentFailedUseCase paymentFailedUseCase) {
        this.paymentFailedUseCase = paymentFailedUseCase;
    }

    @ApplicationModuleListener
    public void on(PaymentInitiationFailedEvent event) {
        paymentFailedUseCase
                .handle(new MarkPaymentFailedCommand(event.transactionId(), event.failureReason()));
    }
}
