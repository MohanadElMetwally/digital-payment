package com.example.digital_payment.payment.application.port.in;

import com.example.digital_payment.payment.application.dto.MarkPaymentSucceededCommand;
import com.example.digital_payment.payment.application.dto.MarkPaymentSucceededResult;

public interface MarkPaymentSucceededUseCase {
    MarkPaymentSucceededResult handle(MarkPaymentSucceededCommand command);
}
