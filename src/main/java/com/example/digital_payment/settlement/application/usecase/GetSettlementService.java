package com.example.digital_payment.settlement.application.usecase;

import java.util.UUID;
import com.example.digital_payment.settlement.application.port.in.GetSettlementUseCase;
import com.example.digital_payment.settlement.application.port.out.LoadSettlementPort;
import com.example.digital_payment.settlement.domain.exceptions.SettlementNotFoundException;
import com.example.digital_payment.settlement.domain.models.Settlements;

public class GetSettlementService implements GetSettlementUseCase {
    private final LoadSettlementPort loadSettlementPort;

    public GetSettlementService(LoadSettlementPort loadSettlementPort) {
        this.loadSettlementPort = loadSettlementPort;
    }

    @Override
    public Settlements getByBillId(UUID id) {
        return loadSettlementPort.findById(id).orElseThrow(() -> new SettlementNotFoundException());
    }
}
