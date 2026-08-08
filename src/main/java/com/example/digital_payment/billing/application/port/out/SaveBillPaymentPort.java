package com.example.digital_payment.billing.application.port.out;

import com.example.digital_payment.billing.domain.model.entities.BillPayments;

public interface SaveBillPaymentPort {
    void save(BillPayments billPayment);
}
