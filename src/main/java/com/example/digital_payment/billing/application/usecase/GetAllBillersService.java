package com.example.digital_payment.billing.application.usecase;

import java.util.List;
import com.example.digital_payment.billing.application.dto.BillersResult;
import com.example.digital_payment.billing.application.mapper.BillerMapper;
import com.example.digital_payment.billing.application.port.in.GetAllBillersUseCase;
import com.example.digital_payment.billing.application.port.out.LoadAllBillersPort;
import com.example.digital_payment.billing.domain.model.entities.Billers;

public class GetAllBillersService implements GetAllBillersUseCase {
    private final LoadAllBillersPort loadAllBillersPort;
    private final BillerMapper billerMapper;

    public GetAllBillersService(LoadAllBillersPort loadAllBillersPort, BillerMapper billerMapper) {
        this.loadAllBillersPort = loadAllBillersPort;
        this.billerMapper = billerMapper;
    }

    @Override
    public BillersResult getAll() {
        List<Billers> billers = loadAllBillersPort.findAll();
        return billerMapper.toAllResult(billers);
    }
}
