package com.example.digital_payment.payment.api.controller;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.digital_payment.payment.api.dto.request.PaymentRequest;
import com.example.digital_payment.payment.api.dto.response.PaymentResponse;
import com.example.digital_payment.payment.api.facade.PaymentFacade;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/payments")
public class PaymentController {
    private final PaymentFacade paymentFacade;

    public PaymentController(PaymentFacade paymentFacade) {
        this.paymentFacade = paymentFacade;
    }

    @PostMapping("create-payment")
    public ResponseEntity<PaymentResponse> initiatePayment(
        @RequestHeader("Idempotency-Key") UUID idempotencyKey,
        @Valid @RequestBody PaymentRequest paymentRequest) {
        return ResponseEntity.status(HttpStatus.ACCEPTED)
            .body(paymentFacade.initiatePayment(paymentRequest, idempotencyKey));
    }
}
