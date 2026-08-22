package com.example.digital_payment.wallet.api.facade;

import org.springframework.stereotype.Component;
import com.example.digital_payment.shared.security.CurrentUserContext;
import com.example.digital_payment.wallet.api.dto.WalletResponse;
import com.example.digital_payment.wallet.api.mapper.WalletApiMapper;
import com.example.digital_payment.wallet.application.dto.WalletResult;
import com.example.digital_payment.wallet.application.port.in.GetWalletUseCase;

@Component
public class WalletFacade {
    private final CurrentUserContext currentUserContext;
    private final GetWalletUseCase getWalletUseCase;
    private final WalletApiMapper walletApiMapper;

    public WalletFacade(CurrentUserContext currentUserContext, GetWalletUseCase getWalletUseCase,
            WalletApiMapper walletApiMapper) {
        this.currentUserContext = currentUserContext;
        this.getWalletUseCase = getWalletUseCase;
        this.walletApiMapper = walletApiMapper;
    }

    public WalletResponse getCurrentUserWallet() {
        WalletResult result = getWalletUseCase.getByUserId(currentUserContext.getUserId());
        return walletApiMapper.toResponse(result);
    }
}
