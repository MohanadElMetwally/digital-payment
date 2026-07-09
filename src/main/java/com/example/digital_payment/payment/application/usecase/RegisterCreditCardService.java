package com.example.digital_payment.payment.application.usecase;

import com.example.digital_payment.payment.application.dto.RegisterCreditCardCommand;
import com.example.digital_payment.payment.application.port.in.RegisterCreditCardUseCase;
import com.example.digital_payment.payment.application.port.out.SaveCreditCardPort;
import com.example.digital_payment.payment.domain.model.entities.CreditCards;
import com.example.digital_payment.payment.domain.model.valueobjects.CardRegistrationCreationData;

public class RegisterCreditCardService implements RegisterCreditCardUseCase {
    private final SaveCreditCardPort saveCreditCardPort;

    public RegisterCreditCardService(SaveCreditCardPort saveCreditCardPort) {
        this.saveCreditCardPort = saveCreditCardPort;
    }

    @Override
    public CreditCards registerCreditCard(RegisterCreditCardCommand command) {
        CardRegistrationCreationData creationData = new CardRegistrationCreationData(
            command.userId(), command.brand(), command.lastFour(), command.expiryMonth(),
            command.expiryYear(), command.paymentMethodId(), command.status(), command.isDefault());
        CreditCards card = CreditCards.create(creationData);
        return saveCreditCardPort.save(card);
    }

}
