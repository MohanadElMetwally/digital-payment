package com.example.digital_payment.billing.application.dto;

import java.util.UUID;

public record ProcessBillPaymentCommand(UUID billId, UUID transactionId) {

}
