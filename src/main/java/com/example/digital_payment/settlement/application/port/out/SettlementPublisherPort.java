package com.example.digital_payment.settlement.application.port.out;

import java.util.UUID;

public interface SettlementPublisherPort {
    void publish(UUID id);
}
