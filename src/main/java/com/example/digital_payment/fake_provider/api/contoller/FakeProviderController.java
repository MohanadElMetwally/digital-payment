package com.example.digital_payment.fake_provider.api.contoller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
}
