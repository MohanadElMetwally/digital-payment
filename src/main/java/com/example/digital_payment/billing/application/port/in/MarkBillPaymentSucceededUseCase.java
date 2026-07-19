package com.example.digital_payment.billing.application.port.in;

import com.example.digital_payment.billing.application.dto.ProcessBillPaymentCommand;

public interface MarkBillPaymentSucceededUseCase {
    void mark(ProcessBillPaymentCommand command);
}
