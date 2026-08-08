package com.example.digital_payment.payment.application.port.in;

import com.example.digital_payment.payment.application.dto.InitiatePaymentCommand;
import com.example.digital_payment.payment.domain.model.entities.Transactions;

public interface InitiateWalletTopUpUseCase {
    Transactions initiateWalletTopUp(InitiatePaymentCommand command);
}
