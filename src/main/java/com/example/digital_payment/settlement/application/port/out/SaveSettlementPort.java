package com.example.digital_payment.settlement.application.port.out;

import com.example.digital_payment.settlement.domain.models.Settlements;

public interface SaveSettlementPort {
    void save(Settlements settlement);
}
