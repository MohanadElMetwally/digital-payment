package com.example.digital_payment.settlement.domain.exceptions;

public class InvalidSettlementDataException extends RuntimeException {
    public InvalidSettlementDataException(String msg) {
        super(msg);
    }
}
