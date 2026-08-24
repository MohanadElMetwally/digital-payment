package com.example.digital_payment.billing.api.dto.response;

import java.util.UUID;
import com.example.digital_payment.billing.domain.enums.BillCategory;
import com.example.digital_payment.billing.domain.enums.ServiceProvider;

public record BillerResponse(UUID id, String name, BillCategory category,
        ServiceProvider service_provider, String createdAt) {

}
