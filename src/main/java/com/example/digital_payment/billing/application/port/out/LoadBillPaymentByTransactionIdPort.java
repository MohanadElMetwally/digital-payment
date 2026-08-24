package com.example.digital_payment.billing.application.port.out;

import java.util.Optional;
import java.util.UUID;
import com.example.digital_payment.billing.domain.model.entities.BillPayments;

public interface LoadBillPaymentByTransactionIdPort {
    Optional<BillPayments> findByTransactionId(UUID transactionId);
}
