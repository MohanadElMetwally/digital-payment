package com.example.digital_payment.payment.api.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.digital_payment.payment.api.dto.request.RegisterCreditCardRequest;
import com.example.digital_payment.payment.api.dto.response.CreditCardResponse;
import com.example.digital_payment.payment.api.dto.response.CreditCardsResponse;
import com.example.digital_payment.payment.api.facade.CreditCardFacade;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/cards")
public class CreditCardController {
    private final CreditCardFacade creditCardFacade;

    public CreditCardController(CreditCardFacade creditCardFacade) {
        this.creditCardFacade = creditCardFacade;
    }

    @GetMapping
    public ResponseEntity<CreditCardsResponse> readUserCards() {
        return ResponseEntity.status(HttpStatus.OK).body(creditCardFacade.readCreditCards());
    }

    @PostMapping
    public ResponseEntity<CreditCardResponse> saveCard(
        @Valid @RequestBody RegisterCreditCardRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(creditCardFacade.saveCreditCard(request));
    }

}
