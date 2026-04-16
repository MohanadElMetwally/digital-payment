package com.example.digital_payment.billing.application.usecase;

import java.util.UUID;

import com.example.digital_payment.billing.application.dto.BillerResult;
import com.example.digital_payment.billing.application.mapper.BillerMapper;
import com.example.digital_payment.billing.application.port.in.GetBillerUseCase;
import com.example.digital_payment.billing.application.port.out.LoadBillerPort;
import com.example.digital_payment.billing.domain.exceptions.BillerNotFoundException;
import com.example.digital_payment.billing.domain.model.entities.Billers;

public class GetBillerService implements GetBillerUseCase {
    private final LoadBillerPort loadBillerPort;
    private final BillerMapper billerMapper;

    public GetBillerService(LoadBillerPort loadBillerPort, BillerMapper billerMapper) {
        this.loadBillerPort = loadBillerPort;
        this.billerMapper = billerMapper;
    }

    @Override
    public BillerResult getById(UUID id) {
        Billers biller = loadBillerPort.findById(id)
            .orElseThrow(() -> new BillerNotFoundException(id));
        return billerMapper.toResult(biller);
    }
}
