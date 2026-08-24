package com.example.digital_payment.settlement.application.port.out;

import java.util.List;
import com.example.digital_payment.settlement.domain.models.FailedSettlements;

public interface LoadFailedSettlementsPort {
    List<FailedSettlements> findAll();
}
