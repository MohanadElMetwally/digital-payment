package com.example.digital_payment.billing.application.port.in;

import java.util.UUID;

import com.example.digital_payment.billing.application.dto.BillerResult;

public interface GetBillerUseCase {
    BillerResult getById(UUID id);
}
