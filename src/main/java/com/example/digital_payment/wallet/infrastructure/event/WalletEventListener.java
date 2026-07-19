package com.example.digital_payment.wallet.infrastructure.event;

import org.springframework.modulith.events.ApplicationModuleListener;
import org.springframework.stereotype.Component;

import com.example.digital_payment.shared.dto.CreditWalletEvent;
import com.example.digital_payment.shared.dto.DebitWalletEvent;
import com.example.digital_payment.shared.events.InitiateWalletCreditTransactionEvent;
import com.example.digital_payment.shared.events.InitiateWalletDebitTransactionEvent;
import com.example.digital_payment.shared.events.WalletTransactionFailedEvent;
import com.example.digital_payment.wallet.application.dto.CreateWalletTransactionCommand;
import com.example.digital_payment.wallet.application.dto.CreditWalletCommand;
import com.example.digital_payment.wallet.application.dto.DebitWalletCommand;
import com.example.digital_payment.wallet.application.dto.MarkWalletTransactionFailedCommand;
import com.example.digital_payment.wallet.application.port.in.CreateWalletTransactionUseCase;
import com.example.digital_payment.wallet.application.port.in.CreditWalletUseCase;
import com.example.digital_payment.wallet.application.port.in.DebitWalletUseCase;
import com.example.digital_payment.wallet.application.port.in.MarkWalletTransactionFailedUseCase;
import com.example.digital_payment.wallet.domain.enums.WalletTransactionType;

@Component
public class WalletEventListener {
    private final DebitWalletUseCase debitWalletUseCase;
    private final CreditWalletUseCase creditWalletUseCase;
    private final CreateWalletTransactionUseCase createWalletTransactionUseCase;
    private final MarkWalletTransactionFailedUseCase markWalletTransactionFailedUseCase;

    public WalletEventListener(DebitWalletUseCase debitWalletUseCase,
        CreditWalletUseCase creditWalletUseCase,
        CreateWalletTransactionUseCase createWalletTransactionUseCase,
        MarkWalletTransactionFailedUseCase markWalletTransactionFailedUseCase) {
        this.debitWalletUseCase = debitWalletUseCase;
        this.creditWalletUseCase = creditWalletUseCase;
        this.createWalletTransactionUseCase = createWalletTransactionUseCase;
        this.markWalletTransactionFailedUseCase = markWalletTransactionFailedUseCase;
    }

    @ApplicationModuleListener
    void on(DebitWalletEvent event) {
        debitWalletUseCase.debit(new DebitWalletCommand(event.userId(), event.walletId(),
            event.transactionId(), event.amount()));
    }

    @ApplicationModuleListener
    void on(CreditWalletEvent event) {
        creditWalletUseCase.credit(new CreditWalletCommand(event.userId(), event.walletId(),
            event.transactionId(), event.amount()));
    }

    @ApplicationModuleListener
    void on(InitiateWalletDebitTransactionEvent event) {
        createWalletTransactionUseCase.create(new CreateWalletTransactionCommand(event.walletId(),
            event.transactionId(), WalletTransactionType.DEBIT, event.amount()));
    }

    @ApplicationModuleListener
    void on(InitiateWalletCreditTransactionEvent event) {
        createWalletTransactionUseCase.create(new CreateWalletTransactionCommand(event.walletId(),
            event.transactionId(), WalletTransactionType.CREDIT, event.amount()));
    }

    @ApplicationModuleListener
    void on(WalletTransactionFailedEvent event) {
        markWalletTransactionFailedUseCase
            .mark(new MarkWalletTransactionFailedCommand(event.transactionId()));
    }
}
