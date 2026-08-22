package com.example.digital_payment.payment.infrastructure.stripe;

import java.util.UUID;
import org.springframework.stereotype.Component;
import com.example.digital_payment.payment.application.dto.PaymentFailedEvent;
import com.example.digital_payment.payment.application.dto.PaymentSucceededEvent;
import com.stripe.model.Event;
import com.stripe.model.PaymentIntent;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class StripeEventTranslator {

    public PaymentSucceededEvent toPaymentSucceededEvent(Event event) {
        PaymentIntent pi = extractPaymentIntent(event);
        log.debug("payment successful! transactionID: {} Payment Intent ID: {}",
                transactionIdFrom(pi), pi.getId());
        return new PaymentSucceededEvent(transactionIdFrom(pi), pi.getId(), referenceIdFrom(pi));
    }

    public PaymentFailedEvent toPaymentFailedEvent(Event event) {
        PaymentIntent pi = extractPaymentIntent(event);
        log.debug("payment failed! transaction ID: {}, Payment Intent ID: {}, Reason: {}",
                transactionIdFrom(pi), pi.getId(), failureReasonFrom(pi));
        return new PaymentFailedEvent(transactionIdFrom(pi), failureReasonFrom(pi),
                referenceIdFrom(pi));
    }

    private PaymentIntent extractPaymentIntent(Event event) {
        return (PaymentIntent) event.getDataObjectDeserializer().getObject().orElseThrow();
    }

    private UUID transactionIdFrom(PaymentIntent pi) {
        return UUID.fromString(pi.getMetadata().get("transactionId"));
    }

    private String failureReasonFrom(PaymentIntent pi) {
        return pi.getLastPaymentError() != null ? pi.getLastPaymentError().getMessage() : null;
    }

    private UUID referenceIdFrom(PaymentIntent pi) {
        return UUID.fromString(pi.getMetadata().get("referenceId"));
    }
}
