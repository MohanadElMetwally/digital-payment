package com.example.digital_payment.settlement.application.usecase;

import java.util.UUID;
import com.example.digital_payment.settlement.application.port.in.GetFailedSettlementUseCase;
import com.example.digital_payment.settlement.application.port.out.LoadFailedSettlementPort;
import com.example.digital_payment.settlement.domain.exceptions.FailedSettlementNotFoundException;
import com.example.digital_payment.settlement.domain.models.FailedSettlements;

public class GetFailedSettlementService implements GetFailedSettlementUseCase {
    private final LoadFailedSettlementPort loadFailedSettlementPort;

    public GetFailedSettlementService(LoadFailedSettlementPort loadFailedSettlementPort) {
        this.loadFailedSettlementPort = loadFailedSettlementPort;
    }

    @Override
    public FailedSettlements getById(UUID id) {
        return loadFailedSettlementPort.findById(id)
                .orElseThrow(() -> new FailedSettlementNotFoundException(id));
    }
}
