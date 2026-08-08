package com.example.digital_payment.payment.application.port.out;

import com.example.digital_payment.payment.domain.model.entities.CreditCards;

public interface SaveCreditCardPort {
    CreditCards save(CreditCards card);
}
