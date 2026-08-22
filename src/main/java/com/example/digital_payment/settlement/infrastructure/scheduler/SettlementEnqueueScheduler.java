package com.example.digital_payment.settlement.infrastructure.scheduler;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import com.example.digital_payment.settlement.application.port.in.EnqueueSettlementsUseCase;

@Component
public class SettlementEnqueueScheduler {
    private final EnqueueSettlementsUseCase enqueueSettlementsUseCase;

    public SettlementEnqueueScheduler(EnqueueSettlementsUseCase enqueueSettlementsUseCase) {
        this.enqueueSettlementsUseCase = enqueueSettlementsUseCase;
    }

    @Scheduled(cron = "0 0 9-23 * * *")
    public void run() {
        enqueueSettlementsUseCase.enqueue();
    }
}
