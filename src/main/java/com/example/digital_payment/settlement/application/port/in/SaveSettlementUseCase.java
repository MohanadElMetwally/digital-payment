package com.example.digital_payment.settlement.application.port.in;

import com.example.digital_payment.settlement.application.dto.SaveSettlementCommand;

public interface SaveSettlementUseCase {
    void handle(SaveSettlementCommand command);
}