package com.example.digital_payment.billing.domain.model.valueobjects;

import com.example.digital_payment.billing.domain.enums.BillCategory;
import com.example.digital_payment.billing.domain.enums.ServiceProvider;

public record BillerCreateData(String name, BillCategory category,
    ServiceProvider serviceProvider) {

}
