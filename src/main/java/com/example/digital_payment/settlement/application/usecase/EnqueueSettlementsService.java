package com.example.digital_payment.settlement.application.usecase;

import org.springframework.beans.factory.annotation.Value;

import com.example.digital_payment.settlement.application.port.in.EnqueueSettlementsUseCase;
import com.example.digital_payment.settlement.application.port.out.EnqueueSettlementPort;

public class EnqueueSettlementsService implements EnqueueSettlementsUseCase {

    private final EnqueueSettlementPort enqueueSettlementPort;

    @Value("${app.settlements.batch-size}")
    private int BATCH_SIZE;

    public EnqueueSettlementsService(EnqueueSettlementPort enqueueSettlementPort) {
        this.enqueueSettlementPort = enqueueSettlementPort;
    }

    @Override
    public void enqueue() {
        int claimed;
        do {
            claimed = enqueueSettlementPort.enqueue(BATCH_SIZE);
        } while (claimed > 0);
    }
}