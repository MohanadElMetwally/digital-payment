package com.example.digital_payment.payment.api.facade;

import java.util.List;

import org.springframework.stereotype.Component;

import com.example.digital_payment.payment.api.dto.request.RegisterCreditCardRequest;
import com.example.digital_payment.payment.api.dto.response.CreditCardResponse;
import com.example.digital_payment.payment.api.dto.response.CreditCardsResponse;
import com.example.digital_payment.payment.api.mapper.CreditCardApiMapper;
import com.example.digital_payment.payment.application.dto.RegisterCreditCardCommand;
import com.example.digital_payment.payment.application.port.in.LoadCreditCardsUseCase;
import com.example.digital_payment.payment.application.port.in.RegisterCreditCardUseCase;
import com.example.digital_payment.payment.domain.model.entities.CreditCards;
import com.example.digital_payment.shared.security.CurrentUserContext;

@Component
public class CreditCardFacade {
    private final CurrentUserContext currentUserContext;
    private final LoadCreditCardsUseCase loadCreditCardsUseCase;
    private final RegisterCreditCardUseCase registerCardUseCase;
    private final CreditCardApiMapper cardApiMapper;

    public CreditCardFacade(CurrentUserContext currentUserContext,
        LoadCreditCardsUseCase loadCreditCardsUseCase,
        RegisterCreditCardUseCase registerCardUseCase, CreditCardApiMapper cardApiMapper) {
        this.currentUserContext = currentUserContext;
        this.loadCreditCardsUseCase = loadCreditCardsUseCase;
        this.registerCardUseCase = registerCardUseCase;
        this.cardApiMapper = cardApiMapper;
    }

    public CreditCardsResponse readCreditCards() {
        List<CreditCards> cards = loadCreditCardsUseCase
            .loadCreditCards(currentUserContext.getUserId());
        return new CreditCardsResponse(cards);
    }

    public CreditCardResponse saveCreditCard(RegisterCreditCardRequest request) {
        RegisterCreditCardCommand command = cardApiMapper
            .toRegisterCreditCardCommand(currentUserContext.getUserId(), request);
        CreditCards card = registerCardUseCase.registerCreditCard(command);
        return cardApiMapper.toResponse(card);
    }
}
