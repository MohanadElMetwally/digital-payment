package com.example.digital_payment.billing.api.controller;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.digital_payment.billing.api.dto.response.BillResponse;
import com.example.digital_payment.billing.api.facade.BillsFacade;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/bills")
public class BillController {
    private final BillsFacade billFacade;

    public BillController(BillsFacade billFacade) {
        this.billFacade = billFacade;
    }

    @GetMapping("/{customerNumber}")
    public ResponseEntity<BillResponse> fetchBill(@PathVariable String customerNumber,
        @Valid @RequestParam UUID billerId) {
        return ResponseEntity.status(HttpStatus.OK)
            .body(billFacade.fetchBillByCustomerNumber(billerId, customerNumber));
    }

}
