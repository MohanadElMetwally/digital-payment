package com.example.digital_payment.wallet.api.mapper;

import org.springframework.stereotype.Component;

import com.example.digital_payment.wallet.api.dto.WalletResponse;
import com.example.digital_payment.wallet.application.dto.WalletResult;

@Component
public class WalletApiMapper {
    public WalletResponse toResponse(WalletResult result) {
        return new WalletResponse(result.id(), result.userId(),  result.balance(),
            result.currency(), result.createdAt().toString());
    }
}
