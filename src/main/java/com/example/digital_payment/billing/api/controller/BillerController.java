package com.example.digital_payment.billing.api.controller;

import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.digital_payment.billing.api.dto.response.BillerResponse;
import com.example.digital_payment.billing.api.dto.response.BillersResponse;
import com.example.digital_payment.billing.api.facade.BillersFacade;

@RestController
@RequestMapping("/billers")
public class BillerController {
    private final BillersFacade billersFacade;

    public BillerController(BillersFacade billersFacade) {
        this.billersFacade = billersFacade;
    }

    @GetMapping
    public ResponseEntity<BillersResponse> readBillers() {
        return ResponseEntity.status(HttpStatus.OK).body(billersFacade.getBillers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BillerResponse> readBillerById(@PathVariable UUID id) {
        return ResponseEntity.status(HttpStatus.OK).body(billersFacade.getBillerById(id));
    }
}
