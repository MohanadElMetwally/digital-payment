package com.example.digital_payment.shared.dto;

import java.util.UUID;

public record BillPaymentFailedEvent(UUID transactionId, UUID billId) {

}
