package com.example.digital_payment.notification.application.usecase;

import java.util.UUID;

import com.example.digital_payment.notification.application.port.in.NotificationReadUseCase;
import com.example.digital_payment.notification.application.port.out.LoadNotificationPort;
import com.example.digital_payment.notification.application.port.out.UpdateNotificationPort;
import com.example.digital_payment.notification.domain.exception.NotificationNotFoundException;
import com.example.digital_payment.notification.domain.model.entities.Notifications;
import com.example.digital_payment.shared.application.port.out.TransactionPort;

public class NotificationReadService implements NotificationReadUseCase {

    private final LoadNotificationPort loadNotificationPort;
    private final UpdateNotificationPort updateNotificationPort;
    private final TransactionPort transactionPort;

    public NotificationReadService(LoadNotificationPort loadNotificationPort,
        UpdateNotificationPort updateNotificationPort, TransactionPort transactionPort) {
        this.loadNotificationPort = loadNotificationPort;
        this.updateNotificationPort = updateNotificationPort;
        this.transactionPort = transactionPort;
    }

    @Override
    public void NotificationRead(UUID id) {
        transactionPort.executeVoid(() -> {
            Notifications notification = loadNotificationPort.findById(id)
                .orElseThrow(() -> new NotificationNotFoundException(
                    "Notification not found, Skipping mark as read"));
            if (!notification.getIsRead()) {
                notification.markRead();
                updateNotificationPort.UpdateNotification(id, notification);
            }
        });
    }
}
