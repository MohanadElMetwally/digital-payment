package com.example.digital_payment.payment.application.port.in;

import java.util.List;
import java.util.UUID;
import com.example.digital_payment.payment.domain.model.entities.CreditCards;

public interface LoadCreditCardsUseCase {
    List<CreditCards> loadCreditCards(UUID userId);
}
