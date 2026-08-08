package com.example.digital_payment.shared.events;

import java.util.UUID;

public record InitiateBillPaymentFailedEvent(UUID billId, UUID transactionId) {

}
