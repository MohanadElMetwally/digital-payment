package com.example.digital_payment.settlement.application.usecase;

import java.util.List;
import com.example.digital_payment.settlement.application.port.in.GetFailedSettlementsUseCase;
import com.example.digital_payment.settlement.application.port.out.LoadFailedSettlementsPort;
import com.example.digital_payment.settlement.domain.models.FailedSettlements;

public class GetFailedSettlementsService implements GetFailedSettlementsUseCase {
    private final LoadFailedSettlementsPort loadFailedSettlementsPort;

    public GetFailedSettlementsService(LoadFailedSettlementsPort loadFailedSettlementsPort) {
        this.loadFailedSettlementsPort = loadFailedSettlementsPort;
    }

    @Override
    public List<FailedSettlements> findAll() {
        return loadFailedSettlementsPort.findAll();
    }
}
