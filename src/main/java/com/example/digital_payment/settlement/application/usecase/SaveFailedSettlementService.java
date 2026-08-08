package com.example.digital_payment.settlement.application.usecase;

import java.util.UUID;

import com.example.digital_payment.settlement.application.port.in.SaveFailedSettlementUseCase;
import com.example.digital_payment.settlement.application.port.out.LoadSettlementPort;
import com.example.digital_payment.settlement.application.port.out.SaveFailedSettlementPort;
import com.example.digital_payment.settlement.domain.exceptions.SettlementNotFoundException;
import com.example.digital_payment.settlement.domain.models.FailedSettlements;
import com.example.digital_payment.settlement.domain.models.Settlements;
import com.example.digital_payment.settlement.domain.valueobjects.FailedSettlementCreationData;

public class SaveFailedSettlementService implements SaveFailedSettlementUseCase {
    private final SaveFailedSettlementPort saveFailedSettlementPort;
    private final LoadSettlementPort loadSettlementPort;

    public SaveFailedSettlementService(SaveFailedSettlementPort saveFailedSettlementPort,
        LoadSettlementPort loadSettlementPort) {
        this.saveFailedSettlementPort = saveFailedSettlementPort;
        this.loadSettlementPort = loadSettlementPort;
    }

    @Override
    public void save(UUID settlementId) {
        Settlements settlement = loadSettlementPort.findById(settlementId)
            .orElseThrow(() -> new SettlementNotFoundException());
        FailedSettlements failedSettlement = FailedSettlements
            .create(new FailedSettlementCreationData(settlementId, settlement.getBillId(),
                settlement.getUserId(), settlement.getCustomerNumber(), settlement.getAmount(),
                settlement.getCurrency(), settlement.getLastError(), settlement.getAttemptCount()));
        saveFailedSettlementPort.save(failedSettlement);
    }
}