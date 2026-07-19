package com.example.digital_payment.shared.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record WalletInfo(UUID walletId, String currency, BigDecimal balance) {

}
