package com.example.digital_payment.settlement.application.port.out;

import java.util.Optional;
import java.util.UUID;
import com.example.digital_payment.settlement.domain.models.SettlementsOutbox;

public interface LoadSettlementsOutboxPort {
    Optional<SettlementsOutbox> findBySettlementId(UUID settlementId);
}
