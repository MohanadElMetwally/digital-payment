package com.example.digital_payment.settlement.application.port.out;

public interface EnqueueSettlementPort {
    int enqueue(int batchSize);
}
