package com.example.digital_payment.billing.infrastructure.event;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.modulith.events.ApplicationModuleListener;
import org.springframework.stereotype.Component;
import com.example.digital_payment.billing.application.dto.ProcessBillPaymentCommand;
import com.example.digital_payment.billing.application.port.in.InitiateBillPaymentUseCase;
import com.example.digital_payment.billing.application.port.in.MarkBillPaymentFailedUseCase;
import com.example.digital_payment.billing.application.port.in.MarkBillPaymentSucceededUseCase;
import com.example.digital_payment.billing.domain.model.entities.Bills;
import com.example.digital_payment.shared.events.BillPaymentFailedEvent;
import com.example.digital_payment.shared.events.BillPaymentSucceededEvent;
import com.example.digital_payment.shared.events.CreateSettlementEvent;
import com.example.digital_payment.shared.events.InitiateBillPaymentEvent;
import com.example.digital_payment.shared.events.InitiateBillPaymentFailedEvent;

@Component
public class BillingEventListener {
    private final InitiateBillPaymentUseCase initiateBillPaymentUseCase;
    private final MarkBillPaymentFailedUseCase markBillPaymentFailedUseCase;
    private final MarkBillPaymentSucceededUseCase markBillPaymentSucceededUseCase;
    private final ApplicationEventPublisher eventPublisher;

    public BillingEventListener(InitiateBillPaymentUseCase initiateBillPaymentUseCase,
            MarkBillPaymentFailedUseCase markBillPaymentFailedUseCase,
            MarkBillPaymentSucceededUseCase markBillPaymentSucceededUseCase,
            ApplicationEventPublisher eventPublisher) {
        this.initiateBillPaymentUseCase = initiateBillPaymentUseCase;
        this.markBillPaymentFailedUseCase = markBillPaymentFailedUseCase;
        this.markBillPaymentSucceededUseCase = markBillPaymentSucceededUseCase;
        this.eventPublisher = eventPublisher;
    }

    @EventListener
    public void on(InitiateBillPaymentEvent event) {
        initiateBillPaymentUseCase
                .initiate(new ProcessBillPaymentCommand(event.billId(), event.transactionId()));
    }

    @ApplicationModuleListener
    public void on(InitiateBillPaymentFailedEvent event) {
        markBillPaymentFailedUseCase
                .mark(new ProcessBillPaymentCommand(event.billId(), event.transactionId()));
    }

    @ApplicationModuleListener
    public void on(BillPaymentSucceededEvent event) {
        Bills bill = markBillPaymentSucceededUseCase
                .mark(new ProcessBillPaymentCommand(event.billId(), event.transactionId()));
        eventPublisher.publishEvent(new CreateSettlementEvent(bill.getId(), bill.getUserId(),
                bill.getExternalCustomerNumber(), bill.getAmount(), bill.getCurrency()));
    }

    @ApplicationModuleListener
    public void on(BillPaymentFailedEvent event) {
        markBillPaymentFailedUseCase
                .mark(new ProcessBillPaymentCommand(event.billId(), event.transactionId()));
    }
}
