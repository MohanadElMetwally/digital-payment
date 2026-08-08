package com.example.digital_payment.wallet.domain.model.valueobjects;

import java.util.UUID;

public record WalletCreationData(UUID userId, String currency) {

}
