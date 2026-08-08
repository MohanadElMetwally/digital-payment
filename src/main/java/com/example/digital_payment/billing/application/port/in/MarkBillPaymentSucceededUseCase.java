package com.example.digital_payment.billing.application.port.in;

import com.example.digital_payment.billing.application.dto.ProcessBillPaymentCommand;
import com.example.digital_payment.billing.domain.model.entities.Bills;

public interface MarkBillPaymentSucceededUseCase {
    Bills mark(ProcessBillPaymentCommand command);
}
