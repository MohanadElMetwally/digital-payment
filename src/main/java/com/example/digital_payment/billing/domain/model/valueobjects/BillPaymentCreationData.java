package com.example.digital_payment.billing.domain.model.valueobjects;

import java.math.BigDecimal;
import java.util.UUID;

public record BillPaymentCreationData(UUID transactionId, UUID billId, BigDecimal amount) {

}
