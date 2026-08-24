package com.example.digital_payment.billing.application.port.out;

import java.util.Optional;
import java.util.UUID;
import com.example.digital_payment.billing.domain.model.entities.Bills;

public interface LoadBillByExternalRefPort {
    Optional<Bills> findByExternalBillId(String externalBillId);

    Optional<Bills> findByExternalBillIdAndBillerId(String externalBillId, UUID billerId);
}
