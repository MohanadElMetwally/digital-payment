package com.example.digital_payment.billing.application.port.out;

import java.util.List;

import com.example.digital_payment.billing.domain.model.entities.Billers;

public interface LoadAllBillersPort {
    List<Billers> findAll();
}
