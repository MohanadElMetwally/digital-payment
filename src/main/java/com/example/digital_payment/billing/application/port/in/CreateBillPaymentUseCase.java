package com.example.digital_payment.billing.application.port.in;

import com.example.digital_payment.billing.application.dto.ProcessBillPaymentCommand;

public interface CreateBillPaymentUseCase {
    void process(ProcessBillPaymentCommand command);
}
