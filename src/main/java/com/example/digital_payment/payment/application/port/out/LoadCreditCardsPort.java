package com.example.digital_payment.payment.application.port.out;

import java.util.List;
import java.util.UUID;

import com.example.digital_payment.payment.domain.model.entities.CreditCards;

public interface LoadCreditCardsPort {
    List<CreditCards> findByUserId(UUID userId);
}
