package com.example.digital_payment.settlement.application.port.out;

import java.util.UUID;

public interface PayProviderGateway {
    String pay(String customerNumber, UUID providerIdempotencyKey);
}
