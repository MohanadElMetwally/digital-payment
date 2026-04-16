package com.example.digital_payment.billing.domain.model.valueobjects;

import java.math.BigDecimal;
import java.util.UUID;

public record BillPaymentCreateData(UUID transaction_id, UUID bill_id, BigDecimal amount) {

}
