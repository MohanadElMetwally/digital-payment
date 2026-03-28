package com.example.digital_payment.wallet.application.port.in;

import com.example.digital_payment.shared.events.UserRegisteredEvent;

public interface CreateWalletUseCase {
    void handle(UserRegisteredEvent event);
}
