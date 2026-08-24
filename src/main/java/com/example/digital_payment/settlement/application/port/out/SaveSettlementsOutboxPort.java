package com.example.digital_payment.settlement.application.port.out;

import com.example.digital_payment.settlement.domain.models.SettlementsOutbox;

public interface SaveSettlementsOutboxPort {
    void save(SettlementsOutbox outbox);
}
