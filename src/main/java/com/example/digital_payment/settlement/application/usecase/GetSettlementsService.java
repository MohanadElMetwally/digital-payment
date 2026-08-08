package com.example.digital_payment.settlement.application.usecase;

import java.util.List;

import com.example.digital_payment.settlement.application.port.in.GetSettlementsUseCase;
import com.example.digital_payment.settlement.application.port.out.LoadSettlementsPort;
import com.example.digital_payment.settlement.domain.models.Settlements;

public class GetSettlementsService implements GetSettlementsUseCase {
    private final LoadSettlementsPort loadSettlementsPort;

    public GetSettlementsService(LoadSettlementsPort loadSettlementsPort) {
        this.loadSettlementsPort = loadSettlementsPort;
    }

    @Override
    public List<Settlements> findAll() {
        return loadSettlementsPort.findAll();
    }
}