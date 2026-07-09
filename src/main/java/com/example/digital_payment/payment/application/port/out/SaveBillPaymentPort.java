package com.example.digital_payment.payment.application.port.out;

import com.example.digital_payment.payment.domain.model.entities.BillPayments;

public interface SaveBillPaymentPort {
    void save(BillPayments billPayment);
}
