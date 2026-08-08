package com.example.digital_payment.settlement.application.port.out;

import com.example.digital_payment.settlement.domain.models.FailedSettlements;

public interface SaveFailedSettlementPort {
    void save(FailedSettlements failedSettlement);
}