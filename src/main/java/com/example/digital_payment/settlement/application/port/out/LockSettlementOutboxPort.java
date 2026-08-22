package com.example.digital_payment.settlement.application.port.out;

import java.util.List;
import com.example.digital_payment.settlement.domain.models.SettlementsOutbox;

public interface LockSettlementOutboxPort {
    List<SettlementsOutbox> lockPendingBatch(int batchSize);
}
