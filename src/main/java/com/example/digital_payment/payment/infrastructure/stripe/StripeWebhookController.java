package com.example.digital_payment.payment.infrastructure.stripe;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/webhooks")
public class StripeWebhookController {
    private final StripeWebhookGateway gateway;

    public StripeWebhookController(StripeWebhookGateway gateway) {
        this.gateway = gateway;
    }

    @PostMapping("/stripe")
    public ResponseEntity<Void> handle(@RequestBody String payload,
        @RequestHeader("Stripe-Signature") String signature) {
        gateway.handle(payload, signature);
        return ResponseEntity.ok().build();
    }
}
