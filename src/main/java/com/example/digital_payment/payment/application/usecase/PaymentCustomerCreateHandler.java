package com.example.digital_payment.payment.application.usecase;

import com.example.digital_payment.payment.application.dto.PaymentCustomerCreateCommand;
import com.example.digital_payment.payment.application.exception.ProviderCustomerException;
import com.example.digital_payment.payment.application.port.in.PaymentCustomerCreateUseCase;
import com.example.digital_payment.payment.application.port.out.PaymentCustomerGatewayPort;
import com.example.digital_payment.payment.application.port.out.SavePaymentCustomerPort;
import com.example.digital_payment.payment.domain.model.entities.PaymentCustomers;
import com.example.digital_payment.payment.domain.model.valueobjects.PaymentCustomerCreationData;

public class PaymentCustomerCreateHandler implements PaymentCustomerCreateUseCase {
    private final PaymentCustomerGatewayPort paymentCustomerGatewayPort;
    private final SavePaymentCustomerPort savePaymentCustomerPort;

    public PaymentCustomerCreateHandler(PaymentCustomerGatewayPort paymentCustomerGatewayPort,
            SavePaymentCustomerPort savePaymentCustomerPort) {
        this.paymentCustomerGatewayPort = paymentCustomerGatewayPort;
        this.savePaymentCustomerPort = savePaymentCustomerPort;
    }

    @Override
    public void handle(PaymentCustomerCreateCommand command) {
        String customerId;
        try {
            customerId = paymentCustomerGatewayPort.createCustomer(command);
        } catch (ProviderCustomerException ex) {
            throw ex;
        }
        PaymentCustomerCreationData creationData =
                new PaymentCustomerCreationData(command.userId(), customerId);
        PaymentCustomers customer = PaymentCustomers.create(creationData);
        savePaymentCustomerPort.save(customer);
    }

}
