package com.example.digital_payment.wallet.infrastructure.event;

import org.springframework.modulith.events.ApplicationModuleListener;
import org.springframework.stereotype.Component;

import com.example.digital_payment.shared.events.UserRegisteredEvent;
import com.example.digital_payment.wallet.application.dto.CreateWalletCommand;
import com.example.digital_payment.wallet.application.port.in.CreateWalletUseCase;

@Component
public class CreateWalletEventListener {
    private final CreateWalletUseCase createWalletUseCase;

    public CreateWalletEventListener(CreateWalletUseCase createWalletUseCase) {
        this.createWalletUseCase = createWalletUseCase;
    }

    @ApplicationModuleListener
    public void on(UserRegisteredEvent event) {
        createWalletUseCase.handle(new CreateWalletCommand(event.userId(), event.currency()));
    }
}