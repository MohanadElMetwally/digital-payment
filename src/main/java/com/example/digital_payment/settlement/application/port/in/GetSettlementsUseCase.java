package com.example.digital_payment.settlement.application.port.in;

import java.util.List;

import com.example.digital_payment.settlement.domain.models.Settlements;

public interface GetSettlementsUseCase {
    List<Settlements> findAll();
}