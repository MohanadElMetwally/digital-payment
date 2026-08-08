package com.example.digital_payment.settlement.application.port.in;

public interface RelaySettlementOutboxUseCase {
    void relayPendingEntries();
}
