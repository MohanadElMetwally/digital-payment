package com.example.digital_payment.payment.application.dto;

import java.math.BigDecimal;
import com.example.digital_payment.shared.dto.WalletInfo;

public record FundingPlan(BigDecimal walletAmount, BigDecimal cardAmount, WalletInfo walletInfo) {
    public static FundingPlan cardOnly(BigDecimal amount) {
        return new FundingPlan(BigDecimal.ZERO, amount, null);
    }

    public boolean isFullyCoveredByWallet() {
        return cardAmount.signum() == 0;
    }
}
