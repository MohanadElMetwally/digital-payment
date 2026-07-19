package com.example.digital_payment.shared.dto;

import java.util.UUID;

public record BillPaymentSucceededEvent(UUID transactionId, UUID billId) {

}
