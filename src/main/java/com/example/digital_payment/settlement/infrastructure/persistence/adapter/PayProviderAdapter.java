package com.example.digital_payment.settlement.infrastructure.persistence.adapter;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.example.digital_payment.settlement.application.port.out.PayProviderGateway;

@Component
public class PayProviderAdapter implements PayProviderGateway {
    @Value("${base.provider.url}")
    private String baseUrl;
    private final RestTemplate restTemplate;

    public PayProviderAdapter(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public String pay(String customerNumber, UUID providerIdempotencyKey) {
        String url = baseUrl + "/pay";
        HttpHeaders headers = new HttpHeaders();
        headers.add("Idempotency-Key", providerIdempotencyKey.toString());
        headers.setContentType(MediaType.TEXT_PLAIN);
        HttpEntity<String> request = new HttpEntity<>(customerNumber, headers);
        String reference = restTemplate.postForObject(url, request, String.class);
        return reference;
    }

}
