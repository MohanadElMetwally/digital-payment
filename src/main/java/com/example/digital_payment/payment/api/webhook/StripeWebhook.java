package com.example.digital_payment.payment.api.webhook;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stripe.exception.SignatureVerificationException;
import com.stripe.model.Event;
import com.stripe.net.Webhook;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/webhooks")
@Slf4j
public class StripeWebhook {
    @Value("${stripe.webhook.secret}")
    private String webhookSecret;

    @PostMapping("/stripe")
    public ResponseEntity<Void> handle(@RequestBody String payload,
        @RequestHeader("Stripe-Signature") String signature) {
        log.debug("payload received: {}, signature: {}", payload, signature);
        Event event;
        try {
            event = Webhook.constructEvent(payload, signature, webhookSecret);
            log.debug("reconstructed event: {}", event);
        } catch (SignatureVerificationException e) {
            log.error("failed to parse webhook event. ", e);
        }
        return ResponseEntity.ok().build();
    }
}
