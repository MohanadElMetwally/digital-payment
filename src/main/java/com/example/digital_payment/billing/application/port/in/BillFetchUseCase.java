package com.example.digital_payment.billing.application.port.in;

import com.example.digital_payment.billing.application.dto.BillFetchCommand;
import com.example.digital_payment.billing.application.dto.BillResult;

public interface BillFetchUseCase {
    BillResult fetchBill(BillFetchCommand command);
}
