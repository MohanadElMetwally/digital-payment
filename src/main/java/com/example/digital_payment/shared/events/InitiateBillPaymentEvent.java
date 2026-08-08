package com.example.digital_payment.shared.events;

import java.util.UUID;

public record InitiateBillPaymentEvent(UUID billId, UUID transactionId) {

}
