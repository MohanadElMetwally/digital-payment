package com.example.digital_payment.settlement.application.usecase;

import java.util.UUID;
import java.util.function.Consumer;
import org.springframework.beans.factory.annotation.Value;
import com.example.digital_payment.settlement.application.port.in.ProcessSettlementUseCase;
import com.example.digital_payment.settlement.application.port.out.ClaimSettlementPort;
import com.example.digital_payment.settlement.application.port.out.LoadSettlementPort;
import com.example.digital_payment.settlement.application.port.out.PayProviderGateway;
import com.example.digital_payment.settlement.application.port.out.UpdateSettlementPort;
import com.example.digital_payment.settlement.domain.exceptions.SettlementNotFoundException;
import com.example.digital_payment.settlement.domain.models.Settlements;
import com.example.digital_payment.shared.application.port.out.TransactionPort;

public class ProcessSettlementService implements ProcessSettlementUseCase {
    private final LoadSettlementPort loadSettlementPort;
    private final PayProviderGateway payProviderGateway;
    private final UpdateSettlementPort updateSettlementPort;
    private final TransactionPort transactionPort;
    private final ClaimSettlementPort claimSettlementPort;

    @Value("${app.settlements.payment-max-attempts}")
    private int MAX_ATTEMPTS;

    public ProcessSettlementService(LoadSettlementPort loadSettlementPort,
            PayProviderGateway payProviderGateway, UpdateSettlementPort updateSettlementPort,
            TransactionPort transactionPort, ClaimSettlementPort claimSettlementPort) {
        this.loadSettlementPort = loadSettlementPort;
        this.payProviderGateway = payProviderGateway;
        this.updateSettlementPort = updateSettlementPort;
        this.transactionPort = transactionPort;
        this.claimSettlementPort = claimSettlementPort;
    }

    @Override
    public void process(UUID settlementId) {
        boolean claimed = claimSettlementPort.claim(settlementId);
        if (!claimed) {
            return;
        }
        Settlements settlement = loadSettlementPort.findById(settlementId)
                .orElseThrow(() -> new SettlementNotFoundException());
        try {
            String providerReference = payProviderGateway.pay(settlement.getCustomerNumber(),
                    settlement.getProviderIdempotencyKey());
            updateSettlement(settlementId, s -> s.markSucceeded(providerReference));
        } catch (Exception e) {
            updateSettlement(settlementId, s -> s.markFailed(e.getMessage(), MAX_ATTEMPTS));
            throw e;
        }
    }

    private void updateSettlement(UUID settlementId, Consumer<Settlements> mutation) {
        transactionPort.executeVoid(() -> {
            Settlements settlement = loadSettlementPort.findById(settlementId)
                    .orElseThrow(() -> new SettlementNotFoundException());
            mutation.accept(settlement);
            updateSettlementPort.update(settlement);
        });
    }
}
