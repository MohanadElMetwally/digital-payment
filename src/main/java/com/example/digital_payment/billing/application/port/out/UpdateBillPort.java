package com.example.digital_payment.billing.application.port.out;

import com.example.digital_payment.billing.domain.model.entities.Bills;

public interface UpdateBillPort {
    void update(Bills bill);
}
