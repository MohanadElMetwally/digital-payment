package com.example.digital_payment.settlement.application.usecase;

import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;

import org.springframework.beans.factory.annotation.Value;

import com.example.digital_payment.settlement.application.port.in.RelaySettlementOutboxUseCase;
import com.example.digital_payment.settlement.application.port.out.LoadSettlementsOutboxPort;
import com.example.digital_payment.settlement.application.port.out.LockSettlementOutboxPort;
import com.example.digital_payment.settlement.application.port.out.SettlementPublisherPort;
import com.example.digital_payment.settlement.application.port.out.UpdateSettlementsOutboxPort;
import com.example.digital_payment.settlement.domain.exceptions.SettlementNotFoundException;
import com.example.digital_payment.settlement.domain.models.SettlementsOutbox;
import com.example.digital_payment.shared.application.port.out.TransactionPort;

public class RelaySettlementOutboxService implements RelaySettlementOutboxUseCase {

    private final LockSettlementOutboxPort lockSettlementOutboxPort;
    private final SettlementPublisherPort publisher;
    private final LoadSettlementsOutboxPort loadSettlementsOutboxPort;
    private final UpdateSettlementsOutboxPort updateSettlementsOutboxPort;
    private final TransactionPort transactionPort;

    public RelaySettlementOutboxService(LockSettlementOutboxPort lockSettlementOutboxPort,
        SettlementPublisherPort publisher, TransactionPort transactionPort,
        LoadSettlementsOutboxPort loadSettlementsOutboxPort,
        UpdateSettlementsOutboxPort updateSettlementsOutboxPort) {
        this.lockSettlementOutboxPort = lockSettlementOutboxPort;
        this.publisher = publisher;
        this.transactionPort = transactionPort;
        this.loadSettlementsOutboxPort = loadSettlementsOutboxPort;
        this.updateSettlementsOutboxPort = updateSettlementsOutboxPort;
    }

    @Value("${app.settlements.outbox-batch-size}")
    private int BATCH_SIZE;
    @Value("${app.settlements.enqueue-max-attempts}")
    private int MAX_ATTEMPTS;

    @Override
    public void relayPendingEntries() {
        List<SettlementsOutbox> batch;
        do {
            batch = lockSettlementOutboxPort.lockPendingBatch(BATCH_SIZE);
            batch.forEach(this::publishOne);
        } while (!batch.isEmpty());
    }

    private void publishOne(SettlementsOutbox entry) {
        try {
            publisher.publish(entry.getSettlementId());
            updateSettlementOutbox(entry.getSettlementId(), s -> s.markPublished());
        } catch (Exception ex) {
            updateSettlementOutbox(entry.getSettlementId(),
                s -> s.markFailed(ex.getMessage(), MAX_ATTEMPTS));
        }
    }

    private void updateSettlementOutbox(UUID settlementId, Consumer<SettlementsOutbox> mutation) {
        transactionPort.executeVoid(() -> {
            SettlementsOutbox outbox = loadSettlementsOutboxPort
                .findBySettlementId(settlementId)
                .orElseThrow(() -> new SettlementNotFoundException());
            mutation.accept(outbox);
            updateSettlementsOutboxPort.update(outbox);
        });
    }
}