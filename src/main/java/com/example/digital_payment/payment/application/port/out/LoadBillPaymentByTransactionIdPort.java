package com.example.digital_payment.payment.application.port.out;

import java.util.Optional;
import java.util.UUID;

import com.example.digital_payment.payment.domain.model.entities.BillPayments;

public interface LoadBillPaymentByTransactionIdPort {
    Optional<BillPayments> findByTransactionId(UUID transactionId);
}
