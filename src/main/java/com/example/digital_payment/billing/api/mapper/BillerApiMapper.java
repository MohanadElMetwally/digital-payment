package com.example.digital_payment.billing.api.mapper;

import org.springframework.stereotype.Component;
import com.example.digital_payment.billing.api.dto.response.BillerResponse;
import com.example.digital_payment.billing.api.dto.response.BillersResponse;
import com.example.digital_payment.billing.application.dto.BillerResult;
import com.example.digital_payment.billing.application.dto.BillersResult;

@Component
public class BillerApiMapper {
    public BillerResponse toResponse(BillerResult result) {
        return new BillerResponse(result.id(), result.name(), result.category(),
                result.serviceProvider(), result.createdAt().toString());
    }

    public BillersResponse toResponseList(BillersResult result) {
        return new BillersResponse(result.billers().stream().map(this::toResponse).toList());
    }
}
