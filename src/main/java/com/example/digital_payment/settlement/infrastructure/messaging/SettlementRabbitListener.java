package com.example.digital_payment.settlement.infrastructure.messaging;

import java.util.UUID;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.example.digital_payment.settlement.application.port.in.ProcessSettlementUseCase;
import com.example.digital_payment.settlement.application.port.in.SaveFailedSettlementUseCase;

@Component
public class SettlementRabbitListener {
    private final ProcessSettlementUseCase processSettlementUseCase;
    private final SaveFailedSettlementUseCase saveFailedSettlementUseCase;

    public SettlementRabbitListener(ProcessSettlementUseCase processSettlementUseCase,
        SaveFailedSettlementUseCase saveFailedSettlementUseCase) {
        this.processSettlementUseCase = processSettlementUseCase;
        this.saveFailedSettlementUseCase = saveFailedSettlementUseCase;
    }

    @RabbitListener(queues = "settlement.queue")
    void process(UUID settlementId) {
        processSettlementUseCase.process(settlementId);
    }

    @RabbitListener(queues = "settlement.dlq")
    void save(UUID settlementId) {
        saveFailedSettlementUseCase.save(settlementId);
    }
}
