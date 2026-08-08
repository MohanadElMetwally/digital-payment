package com.example.digital_payment.settlement.application.port.in;

import java.util.UUID;

public interface ProcessSettlementUseCase {
    void process(UUID settlementId);
}
