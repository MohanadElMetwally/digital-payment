package com.example.digital_payment.payment.application.usecase;

import java.util.Optional;
import java.util.UUID;
import com.example.digital_payment.payment.application.port.in.LoadCreditCardUseCase;
import com.example.digital_payment.payment.application.port.out.LoadCreditCardPort;
import com.example.digital_payment.payment.domain.exceptions.CardAccessDeniedException;
import com.example.digital_payment.payment.domain.exceptions.CardNotFoundException;
import com.example.digital_payment.payment.domain.model.entities.CreditCards;

public class LoadCreditCardService implements LoadCreditCardUseCase {
    private final LoadCreditCardPort loadCreditCardPort;

    public LoadCreditCardService(LoadCreditCardPort loadCreditCardPort) {
        this.loadCreditCardPort = loadCreditCardPort;
    }

    @Override
    public CreditCards loadCreditCard(UUID userId, UUID cardId) {
        Optional<CreditCards> existingCard = loadCreditCardPort.findById(cardId);

        if (existingCard.isEmpty()) {
            throw new CardNotFoundException("CreditCard was not found");
        }

        CreditCards card = existingCard.get();

        if (!card.belongsTo(userId)) {
            throw new CardAccessDeniedException("Access to credit card denied");
        }

        return card;
    }

}
