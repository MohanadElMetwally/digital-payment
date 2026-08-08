package com.example.digital_payment.payment.domain.exceptions;

import com.example.digital_payment.shared.exception.ForbiddenException;

public class CardAccessDeniedException extends ForbiddenException {
    public CardAccessDeniedException(String message) {
        super(message);
    }
}
