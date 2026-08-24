package com.example.digital_payment.payment.infrastructure.stripe;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import com.example.digital_payment.payment.infrastructure.exception.InvalidWebhookSignatureException;
import com.stripe.exception.SignatureVerificationException;
import com.stripe.model.Event;
import com.stripe.net.Webhook;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class StripeWebhookGateway {

    private final ApplicationEventPublisher publisher;
    private final StripeEventTranslator translator;

    @Value("${stripe.webhook.secret}")
    private String webhookSecret;

    public StripeWebhookGateway(StripeEventTranslator translator,
            ApplicationEventPublisher publisher) {
        this.translator = translator;
        this.publisher = publisher;
    }

    @Transactional
    public void handle(String payload, String signature) {
        Event event = verifySignature(payload, signature);
        StripeEventType type = StripeEventType.fromStripeValue(event.getType());

        switch (type) {
            case PAYMENT_INTENT_SUCCEEDED -> publisher
                    .publishEvent(translator.toPaymentSucceededEvent(event));
            case PAYMENT_INTENT_PAYMENT_FAILED -> publisher
                    .publishEvent(translator.toPaymentFailedEvent(event));
            case UNKNOWN -> {
                return;
            }
        }
    }

    private Event verifySignature(String payload, String signature) {
        try {
            return Webhook.constructEvent(payload, signature, webhookSecret);
        } catch (SignatureVerificationException ex) {
            log.error("failed to parse webhook event. ", ex);
            throw new InvalidWebhookSignatureException(ex);
        }
    }
}
