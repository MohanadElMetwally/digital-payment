package com.example.digital_payment.payment.infrastructure.event;

import org.springframework.modulith.events.ApplicationModuleListener;
import org.springframework.stereotype.Component;

import com.example.digital_payment.payment.application.dto.PaymentCustomerCreateCommand;
import com.example.digital_payment.payment.application.port.in.PaymentCustomerCreateUseCase;
import com.example.digital_payment.shared.events.UserRegisteredEvent;

@Component
public class PaymentCustomerEventListener {
    private final PaymentCustomerCreateUseCase paymentCustomerCreateUseCase;

    public PaymentCustomerEventListener(PaymentCustomerCreateUseCase paymentCustomerCreateUseCase) {
        this.paymentCustomerCreateUseCase = paymentCustomerCreateUseCase;
    }

    @ApplicationModuleListener
    public void on(UserRegisteredEvent event) {
        String fullName = event.firstName() + " " + event.lastName();
        paymentCustomerCreateUseCase
            .handle(new PaymentCustomerCreateCommand(event.userId(), event.email(), fullName));
    }
}
