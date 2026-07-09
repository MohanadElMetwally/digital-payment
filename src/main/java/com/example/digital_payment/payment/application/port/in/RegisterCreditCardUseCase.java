package com.example.digital_payment.payment.application.port.in;

import com.example.digital_payment.payment.application.dto.RegisterCreditCardCommand;
import com.example.digital_payment.payment.domain.model.entities.CreditCards;

public interface RegisterCreditCardUseCase {
    CreditCards registerCreditCard(RegisterCreditCardCommand command);
}
