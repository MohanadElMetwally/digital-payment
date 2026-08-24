package com.example.digital_payment.payment.infrastructure.stripe;

import org.springframework.stereotype.Component;
import com.example.digital_payment.payment.application.dto.PaymentCustomerCreateCommand;
import com.example.digital_payment.payment.application.exception.ProviderCustomerException;
import com.example.digital_payment.payment.application.port.out.PaymentCustomerGatewayPort;
import com.stripe.exception.StripeException;
import com.stripe.model.Customer;
import com.stripe.param.CustomerCreateParams;

@Component
public class StripeCustomerAdapter implements PaymentCustomerGatewayPort {

    @Override
    public String createCustomer(PaymentCustomerCreateCommand command) {

        CustomerCreateParams params =
                CustomerCreateParams.builder().setEmail(command.email()).setName(command.fullName())
                        .putMetadata("userId", command.userId().toString()).build();

        try {
            Customer customer = Customer.create(params);
            return customer.getId();
        } catch (StripeException e) {
            throw new ProviderCustomerException("Failed to create Stripe customer", e);
        }
    }

}
