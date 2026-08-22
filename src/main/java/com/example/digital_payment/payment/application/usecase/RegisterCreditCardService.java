package com.example.digital_payment.payment.application.usecase;

import com.example.digital_payment.payment.application.dto.RegisterCreditCardCommand;
import com.example.digital_payment.payment.application.port.in.RegisterCreditCardUseCase;
import com.example.digital_payment.payment.application.port.out.LoadPaymentCustomerPort;
import com.example.digital_payment.payment.application.port.out.RegisterCardGateway;
import com.example.digital_payment.payment.application.port.out.SaveCreditCardPort;
import com.example.digital_payment.payment.domain.exceptions.PaymentCustomerNotFoundException;
import com.example.digital_payment.payment.domain.model.entities.CreditCards;
import com.example.digital_payment.payment.domain.model.entities.PaymentCustomers;
import com.example.digital_payment.payment.domain.model.valueobjects.CardRegistrationCreationData;

public class RegisterCreditCardService implements RegisterCreditCardUseCase {
    private final SaveCreditCardPort saveCreditCardPort;
    private final LoadPaymentCustomerPort loadPaymentCustomerPort;
    private final RegisterCardGateway gateway;

    public RegisterCreditCardService(SaveCreditCardPort saveCreditCardPort,
            LoadPaymentCustomerPort loadPaymentCustomerPort, RegisterCardGateway gateway) {
        this.saveCreditCardPort = saveCreditCardPort;
        this.loadPaymentCustomerPort = loadPaymentCustomerPort;
        this.gateway = gateway;
    }

    @Override
    public CreditCards registerCreditCard(RegisterCreditCardCommand command) {
        PaymentCustomers customer =
                loadPaymentCustomerPort.findByUserId(command.userId()).orElseThrow(
                        () -> new PaymentCustomerNotFoundException("Payment Customer not found"));
        gateway.register(customer.getCustomerId(), command.paymentMethodId());
        CardRegistrationCreationData creationData =
                new CardRegistrationCreationData(command.userId(), command.brand(),
                        command.lastFour(), command.expiryMonth(), command.expiryYear(),
                        command.paymentMethodId(), command.status(), command.isDefault());
        CreditCards card = CreditCards.create(creationData);
        return saveCreditCardPort.save(card);
    }

}
