package com.example.digital_payment.billing.api.facade;

import java.util.UUID;

import org.springframework.stereotype.Component;

import com.example.digital_payment.billing.api.dto.response.BillerResponse;
import com.example.digital_payment.billing.api.dto.response.BillersResponse;
import com.example.digital_payment.billing.api.mapper.BillerApiMapper;
import com.example.digital_payment.billing.application.dto.BillerResult;
import com.example.digital_payment.billing.application.dto.BillersResult;
import com.example.digital_payment.billing.application.port.in.GetAllBillersUseCase;
import com.example.digital_payment.billing.application.port.in.GetBillerUseCase;

@Component
public class BillersFacade {
    private final GetBillerUseCase getBillerUseCase;
    private final GetAllBillersUseCase getAllBillersUseCase;
    private final BillerApiMapper billerApiMapper;

    public BillersFacade(GetBillerUseCase getBillerUseCase,
        GetAllBillersUseCase getAllBillersUseCase, BillerApiMapper billerApiMapper) {
        this.getAllBillersUseCase = getAllBillersUseCase;
        this.getBillerUseCase = getBillerUseCase;
        this.billerApiMapper = billerApiMapper;
    }

    public BillerResponse getBillerById(UUID id) {
        BillerResult result = getBillerUseCase.getById(id);
        return billerApiMapper.toResponse(result);
    }

    public BillersResponse getBillers() {
        BillersResult result = getAllBillersUseCase.getAll();
        return billerApiMapper.toResponseList(result);
    }
}
