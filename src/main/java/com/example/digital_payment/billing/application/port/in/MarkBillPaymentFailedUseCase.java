package com.example.digital_payment.billing.application.port.in;

import com.example.digital_payment.billing.application.dto.ProcessBillPaymentCommand;

public interface MarkBillPaymentFailedUseCase {
    void mark(ProcessBillPaymentCommand command);
}
