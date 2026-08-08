package com.example.digital_payment.wallet.application.dto;

import java.util.UUID;

public record CreateWalletCommand(UUID userId, String currency) {

}
