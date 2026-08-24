package com.example.digital_payment.payment.application.port.in;

import java.util.UUID;
import com.example.digital_payment.payment.domain.model.entities.CreditCards;

public interface LoadCreditCardUseCase {
    CreditCards loadCreditCard(UUID userId, UUID cardId);
}
