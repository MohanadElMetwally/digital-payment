package com.example.digital_payment.settlement.application.port.in;

import java.util.UUID;
import com.example.digital_payment.settlement.domain.models.FailedSettlements;

public interface GetFailedSettlementUseCase {
    FailedSettlements getById(UUID id);
}
