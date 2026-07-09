package com.example.digital_payment.payment.infrastructure.stripe;

import java.math.BigDecimal;

import org.springframework.stereotype.Component;

import com.example.digital_payment.payment.application.dto.CreatePaymentCommand;
import com.example.digital_payment.payment.application.dto.PaymentInitiationResult;
import com.example.digital_payment.payment.application.exception.PaymentProviderException;
import com.example.digital_payment.payment.application.port.out.PaymentGatewayPort;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.net.RequestOptions;
import com.stripe.param.PaymentIntentCreateParams;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class StripePaymentAdapter implements PaymentGatewayPort {

    @Override
    public PaymentInitiationResult createPayment(CreatePaymentCommand command) {
        try {
            long stripeAmount = convertToStripeAmount(command.amount(), command.currency());
            log.debug("creating payment: amount={}, transaction ID={}", command.amount(),
                command.transactionId());
            PaymentIntentCreateParams params = PaymentIntentCreateParams.builder()
                .setAmount(stripeAmount)
                .setCurrency(command.currency().toLowerCase())
                .setCustomer(command.customerId())
                .setPaymentMethod(command.paymentMethodToken())
                .setConfirm(true)
                .putMetadata("transactionId", command.transactionId().toString())
                .setAutomaticPaymentMethods(
                    PaymentIntentCreateParams.AutomaticPaymentMethods.builder()
                        .setEnabled(true)
                        .setAllowRedirects(
                            PaymentIntentCreateParams.AutomaticPaymentMethods.AllowRedirects.NEVER)
                        .build())
                .build();

            RequestOptions options = RequestOptions.builder()
                .setIdempotencyKey(command.transactionId().toString())
                .build();

            PaymentIntent paymentIntent = PaymentIntent.create(params, options);

            return new PaymentInitiationResult(command.transactionId(), paymentIntent.getId());
        } catch (StripeException ex) {
            log.error("Payment Creation failed: {}", ex);
            throw new PaymentProviderException("Failed to create payment", ex);
        }
    }

    private long convertToStripeAmount(BigDecimal amount, String currency) {

        if ("usd".equalsIgnoreCase(currency) || "egp".equalsIgnoreCase(currency)) {
            return amount.movePointRight(2).longValueExact();
        }

        return amount.longValueExact();
    }

}
