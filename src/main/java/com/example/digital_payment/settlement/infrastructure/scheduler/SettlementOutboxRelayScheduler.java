package com.example.digital_payment.settlement.infrastructure.scheduler;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.example.digital_payment.settlement.application.port.in.RelaySettlementOutboxUseCase;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
class SettlementOutboxRelayScheduler {
    private final RelaySettlementOutboxUseCase relaySettlementOutboxUseCase;

    public SettlementOutboxRelayScheduler(
        RelaySettlementOutboxUseCase relaySettlementOutboxUseCase) {
        this.relaySettlementOutboxUseCase = relaySettlementOutboxUseCase;
    }

    @Scheduled(cron = "0 0 22 * * *")
    public void run() {
        log.debug("relaying entries now to pay providers!!!");
        relaySettlementOutboxUseCase.relayPendingEntries();
    }
}