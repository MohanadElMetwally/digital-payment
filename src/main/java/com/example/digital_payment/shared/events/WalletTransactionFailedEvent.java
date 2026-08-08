package com.example.digital_payment.shared.events;

import java.util.UUID;

public record WalletTransactionFailedEvent(UUID transactionId) {

}
