package com.example.digital_payment.billing.application.port.out;

import com.example.digital_payment.billing.domain.model.entities.Bills;

public interface SaveBillPort {
    Bills save(Bills bill);
}
