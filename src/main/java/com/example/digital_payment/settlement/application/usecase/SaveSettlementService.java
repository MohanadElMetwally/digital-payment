package com.example.digital_payment.settlement.application.usecase;

import com.example.digital_payment.settlement.application.dto.SaveSettlementCommand;
import com.example.digital_payment.settlement.application.port.in.SaveSettlementUseCase;
import com.example.digital_payment.settlement.application.port.out.SaveSettlementPort;
import com.example.digital_payment.settlement.domain.models.Settlements;
import com.example.digital_payment.settlement.domain.valueobjects.SettlementCreationData;

public class SaveSettlementService implements SaveSettlementUseCase {
    private final SaveSettlementPort saveSettlementPort;

    public SaveSettlementService(SaveSettlementPort saveSettlementPort) {
        this.saveSettlementPort = saveSettlementPort;
    }

    @Override
    public void handle(SaveSettlementCommand command) {
        Settlements settlement = Settlements.create(new SettlementCreationData(command.billId(),
                command.userId(), command.customerNumber(), command.amount(), command.currency()));
        saveSettlementPort.save(settlement);
    }
}
