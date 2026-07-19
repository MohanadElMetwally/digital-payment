package com.example.digital_payment.billing.infrastructure.event;

import org.springframework.modulith.events.ApplicationModuleListener;
import org.springframework.stereotype.Component;

import com.example.digital_payment.billing.application.dto.ProcessBillPaymentCommand;
import com.example.digital_payment.billing.application.port.in.CreateBillPaymentUseCase;
import com.example.digital_payment.billing.application.port.in.MarkBillPaymentFailedUseCase;
import com.example.digital_payment.billing.application.port.in.MarkBillPaymentSucceededUseCase;
import com.example.digital_payment.shared.dto.BillPaymentFailedEvent;
import com.example.digital_payment.shared.dto.BillPaymentSucceededEvent;
import com.example.digital_payment.shared.events.InitiateBillPaymentEvent;
import com.example.digital_payment.shared.events.InitiateBillPaymentFailedEvent;

@Component
public class BillingEventListener {
    private final CreateBillPaymentUseCase createBillPaymentUseCase;
    private final MarkBillPaymentFailedUseCase markBillPaymentFailedUseCase;
    private final MarkBillPaymentSucceededUseCase markBillPaymentSucceededUseCase;

    public BillingEventListener(CreateBillPaymentUseCase createBillPaymentUseCase,
        MarkBillPaymentFailedUseCase markBillPaymentFailedUseCase,
        MarkBillPaymentSucceededUseCase markBillPaymentSucceededUseCase) {
        this.createBillPaymentUseCase = createBillPaymentUseCase;
        this.markBillPaymentFailedUseCase = markBillPaymentFailedUseCase;
        this.markBillPaymentSucceededUseCase = markBillPaymentSucceededUseCase;
    }

    @ApplicationModuleListener
    public void on(InitiateBillPaymentEvent event) {
        createBillPaymentUseCase
            .process(new ProcessBillPaymentCommand(event.billId(), event.transactionId()));
    }

    @ApplicationModuleListener
    public void on(InitiateBillPaymentFailedEvent event) {
        markBillPaymentFailedUseCase
            .mark(new ProcessBillPaymentCommand(event.billId(), event.transactionId()));
    }

    @ApplicationModuleListener
    public void on(BillPaymentSucceededEvent event) {
        markBillPaymentSucceededUseCase
            .mark(new ProcessBillPaymentCommand(event.billId(), event.transactionId()));
    }

    @ApplicationModuleListener
    public void on(BillPaymentFailedEvent event) {
        markBillPaymentFailedUseCase
            .mark(new ProcessBillPaymentCommand(event.billId(), event.transactionId()));
    }
}
