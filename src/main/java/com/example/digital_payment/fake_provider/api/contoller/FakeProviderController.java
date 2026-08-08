package com.example.digital_payment.fake_provider.api.contoller;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.digital_payment.fake_provider.api.dto.FakeBillResponse;
import com.example.digital_payment.fake_provider.api.facade.FakeProviderFacade;

@RestController
@RequestMapping("/fake-providers/bills")
public class FakeProviderController {
    private final FakeProviderFacade fakeProviderFacade;

    public FakeProviderController(FakeProviderFacade fakeProviderFacade) {
        this.fakeProviderFacade = fakeProviderFacade;
    }

    @GetMapping("/{customerNumber}")
    public ResponseEntity<FakeBillResponse> readFakeBill(@PathVariable String customerNumber) {
        return ResponseEntity.status(HttpStatus.OK)
            .body(fakeProviderFacade.getFakeBillByCustomerNumber(customerNumber));
    }

    @PostMapping("/pay")
    public ResponseEntity<String> payFakeBill(
        @RequestHeader("Idempotency-Key") UUID idempotencyKey, @RequestBody String customerNumber) {
        fakeProviderFacade.markFakeBillPaid(customerNumber);
        return ResponseEntity.ok(UUID.randomUUID().toString());
    }
}
