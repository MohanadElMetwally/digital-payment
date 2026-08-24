package com.example.digital_payment.payment.infrastructure.stripe;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.modulith.events.ApplicationModuleListener;
import org.springframework.stereotype.Component;
import com.example.digital_payment.payment.application.dto.MarkPaymentFailedCommand;
import com.example.digital_payment.payment.application.dto.MarkPaymentFailedResult;
import com.example.digital_payment.payment.application.dto.MarkPaymentSucceededCommand;
import com.example.digital_payment.payment.application.dto.MarkPaymentSucceededResult;
import com.example.digital_payment.payment.application.dto.PaymentFailedEvent;
import com.example.digital_payment.payment.application.dto.PaymentNotificationPayload;
import com.example.digital_payment.payment.application.dto.PaymentSucceededEvent;
import com.example.digital_payment.payment.application.dto.WalletTopUpNotificationPayload;
import com.example.digital_payment.payment.application.enums.MarkPaymentStatus;
import com.example.digital_payment.payment.application.port.in.MarkPaymentFailedUseCase;
import com.example.digital_payment.payment.application.port.in.MarkPaymentSucceededUseCase;
import com.example.digital_payment.payment.application.port.out.TransactionCacheStore;
import com.example.digital_payment.payment.domain.model.entities.Transactions;
import com.example.digital_payment.shared.domain.enums.SseNotificationType;
import com.example.digital_payment.shared.dto.NotificationMessage;
import com.example.digital_payment.shared.events.BillPaymentFailedEvent;
import com.example.digital_payment.shared.events.BillPaymentSucceededEvent;
import com.example.digital_payment.shared.events.CreditWalletEvent;
import com.example.digital_payment.shared.events.SseNotificationEvent;
import com.example.digital_payment.shared.events.WalletTransactionFailedEvent;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class StripeWebhookEventListner {
    private final MarkPaymentSucceededUseCase markSucceeded;
    private final MarkPaymentFailedUseCase markFailed;
    private final ApplicationEventPublisher publisher;
    private final TransactionCacheStore cacheStore;

    public StripeWebhookEventListner(MarkPaymentSucceededUseCase markSucceeded,
            MarkPaymentFailedUseCase markFailed, ApplicationEventPublisher publisher,
            TransactionCacheStore cacheStore) {
        this.markSucceeded = markSucceeded;
        this.markFailed = markFailed;
        this.publisher = publisher;
        this.cacheStore = cacheStore;
    }

    @ApplicationModuleListener
    void on(PaymentSucceededEvent event) {
        MarkPaymentSucceededResult result = markSucceeded.handle(
                new MarkPaymentSucceededCommand(event.transactionId(), event.externalReference()));

        Transactions tx = result.transaction();

        if (result.status() != MarkPaymentStatus.PAYMENT_RECORDED) {
            log.debug("Ignoring payment success for transaction {} because it's already {}",
                    tx.getId(), tx.getStatus());
            return;
        }

        cacheStore.save(tx.getUserId(), tx.getIdempotencyKey(), tx);

        switch (tx.getType()) {
            case PAYMENT -> {
                log.info("Bill payment success for bill ID {} and transaction ID: {}",
                        event.referenceId(), tx.getId());
                publisher.publishEvent(
                        new BillPaymentSucceededEvent(tx.getId(), event.referenceId()));
                publisher.publishEvent(new SseNotificationEvent<>(tx.getUserId(),
                        new NotificationMessage<>(SseNotificationType.PAYMENT_SUCCESS,
                                new PaymentNotificationPayload(tx.getId(), tx.getStatus()))));
            }
            case WALLET_TOP_UP -> {
                log.info("Wallet top-up payment succeeded for Wallet ID {}, transaction ID: {}",
                        event.referenceId(), tx.getId());
                publisher.publishEvent(new CreditWalletEvent(tx.getUserId(), event.referenceId(),
                        tx.getId(), tx.getAmount()));
                publisher.publishEvent(new SseNotificationEvent<>(tx.getUserId(),
                        new NotificationMessage<>(SseNotificationType.WALLET_TOP_UP_SUCCESS,
                                new WalletTopUpNotificationPayload(tx.getId(), tx.getStatus(),
                                        tx.getAmount()))));
            }
        }
    }

    @ApplicationModuleListener
    void on(PaymentFailedEvent event) {
        MarkPaymentFailedResult result = markFailed
                .handle(new MarkPaymentFailedCommand(event.transactionId(), event.failureReason()));

        Transactions tx = result.transaction();

        if (result.status() != MarkPaymentStatus.PAYMENT_RECORDED) {
            log.debug("Ignoring payment failed for transaction {} because it's already {}",
                    tx.getId(), tx.getStatus());
            return;
        }

        cacheStore.save(tx.getUserId(), tx.getIdempotencyKey(), tx);

        switch (tx.getType()) {
            case PAYMENT -> {
                log.info("Bill payment failed for bill ID {}, transaction ID: {}, reason: {}",
                        event.referenceId(), tx.getId(), tx.getFailureReason());
                publisher.publishEvent(new BillPaymentFailedEvent(tx.getId(), event.referenceId()));
                publisher.publishEvent(new SseNotificationEvent<>(tx.getUserId(),
                        new NotificationMessage<>(SseNotificationType.PAYMENT_FAILED,
                                new PaymentNotificationPayload(tx.getId(), tx.getStatus(),
                                        tx.getFailureReason()))));
            }
            case WALLET_TOP_UP -> {
                log.info("Wallet top-up failed for walletID {}, transactionID: {}, reason: {}",
                        event.referenceId(), tx.getId(), tx.getFailureReason());
                publisher.publishEvent(new WalletTransactionFailedEvent(tx.getId()));
                publisher.publishEvent(new SseNotificationEvent<>(tx.getUserId(),
                        new NotificationMessage<>(SseNotificationType.WALLET_TOP_UP_FAILED,
                                new WalletTopUpNotificationPayload(tx.getId(), tx.getStatus(),
                                        tx.getAmount()))));
            }
        }
    }
}
