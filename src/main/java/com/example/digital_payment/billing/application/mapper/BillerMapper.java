package com.example.digital_payment.billing.application.mapper;

import java.util.List;

import com.example.digital_payment.billing.application.dto.BillerResult;
import com.example.digital_payment.billing.application.dto.BillersResult;
import com.example.digital_payment.billing.domain.model.entities.Billers;

public class BillerMapper {
    public BillerResult toResult(Billers biller) {
        return new BillerResult(biller.getId(), biller.getName(), biller.getCategory(),
            biller.getServiceProvider(), biller.getCreatedAt());
    }

    public BillersResult toAllResult(List<Billers> billers) {
        return new BillersResult(billers.stream().map(this::toResult).toList());
    }
}
