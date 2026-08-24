package com.example.digital_payment.payment.infrastructure.stripe;

import org.springframework.stereotype.Component;
import com.example.digital_payment.payment.application.exception.PaymentProviderException;
import com.example.digital_payment.payment.application.port.out.RegisterCardGateway;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentMethod;
import com.stripe.param.PaymentMethodAttachParams;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class StripeRegisterCard implements RegisterCardGateway {

    @Override
    public void register(String customerId, String paymentMethodId) {
        try {
            PaymentMethod paymentMethod = PaymentMethod.retrieve(paymentMethodId);
            PaymentMethodAttachParams params =
                    PaymentMethodAttachParams.builder().setCustomer(customerId).build();
            paymentMethod.attach(params);
        } catch (StripeException ex) {
            log.error("Failed to register credit card: {}", ex);
            throw new PaymentProviderException(ex.getMessage(), ex);
        }
    }

}
