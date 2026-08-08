package com.example.digital_payment.shared.events;

import java.util.UUID;

public record BillPaymentSucceededEvent(UUID transactionId, UUID billId) {

}
