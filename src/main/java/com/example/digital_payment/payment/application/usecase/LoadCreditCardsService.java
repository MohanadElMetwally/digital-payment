package com.example.digital_payment.payment.application.usecase;

import java.util.List;
import java.util.UUID;
import com.example.digital_payment.payment.application.port.in.LoadCreditCardsUseCase;
import com.example.digital_payment.payment.application.port.out.LoadCreditCardsPort;
import com.example.digital_payment.payment.domain.model.entities.CreditCards;

public class LoadCreditCardsService implements LoadCreditCardsUseCase {
    private final LoadCreditCardsPort loadCreditCardsPort;

    public LoadCreditCardsService(LoadCreditCardsPort loadCreditCardsPort) {
        this.loadCreditCardsPort = loadCreditCardsPort;
    }

    @Override
    public List<CreditCards> loadCreditCards(UUID userId) {
        return loadCreditCardsPort.findByUserId(userId);
    }

}
