package com.example.digital_payment.settlement.infrastructure.events;

import org.springframework.modulith.events.ApplicationModuleListener;
import org.springframework.stereotype.Component;
import com.example.digital_payment.settlement.application.dto.SaveSettlementCommand;
import com.example.digital_payment.settlement.application.port.in.SaveSettlementUseCase;
import com.example.digital_payment.shared.events.CreateSettlementEvent;

@Component
public class SettlementsEventListener {
    private final SaveSettlementUseCase saveSettlementUseCase;

    public SettlementsEventListener(SaveSettlementUseCase saveSettlementUseCase) {
        this.saveSettlementUseCase = saveSettlementUseCase;
    }

    @ApplicationModuleListener
    public void on(CreateSettlementEvent event) {
        saveSettlementUseCase.handle(new SaveSettlementCommand(event.billId(), event.userId(),
                event.customerNumber(), event.amount(), event.currency()));
    }
}
