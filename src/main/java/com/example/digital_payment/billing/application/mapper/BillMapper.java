package com.example.digital_payment.billing.application.mapper;

import com.example.digital_payment.billing.application.dto.BillResult;
import com.example.digital_payment.billing.domain.model.entities.Bills;

public class BillMapper {
    public BillResult toResult(Bills bill) {
        return new BillResult(bill.getId(), bill.getBillerId(), bill.getUserId(),
                bill.getExternalCustomerNumber(), bill.getExternalCustomerName(),
                bill.getExternalBillId(), bill.getAmount(), bill.getCurrency(), bill.getStatus(),
                bill.getBillingPeriodStart(), bill.getBillingPeriodEnd(), bill.getDueDate(),
                bill.getLastSyncedAt(), bill.getCreatedAt());
    }
}
