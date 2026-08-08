package com.example.digital_payment.payment.api.dto.response;

import java.math.BigDecimal;

public record PaymentResponse(String referenceNumber, String type, String status, BigDecimal amount,
    String currency) {

}
