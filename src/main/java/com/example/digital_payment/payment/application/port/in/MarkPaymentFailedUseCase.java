package com.example.digital_payment.payment.application.port.in;

import com.example.digital_payment.payment.application.dto.MarkPaymentFailedCommand;
import com.example.digital_payment.payment.application.dto.MarkPaymentFailedResult;

public interface MarkPaymentFailedUseCase {
    MarkPaymentFailedResult handle(MarkPaymentFailedCommand command);
}
