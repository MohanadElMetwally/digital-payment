package com.example.digital_payment.settlement.application.port.in;

import java.util.List;

import com.example.digital_payment.settlement.domain.models.FailedSettlements;

public interface GetFailedSettlementsUseCase {
    List<FailedSettlements> findAll();
}