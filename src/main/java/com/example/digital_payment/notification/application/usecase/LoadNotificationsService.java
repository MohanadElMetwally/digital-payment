package com.example.digital_payment.notification.application.usecase;

import java.util.List;
import com.example.digital_payment.notification.application.dto.NotificationsResult;
import com.example.digital_payment.notification.application.mapper.NotificationMapper;
import com.example.digital_payment.notification.application.port.in.LoadNotificationsUseCase;
import com.example.digital_payment.notification.application.port.out.LoadNotificationsPort;
import com.example.digital_payment.notification.domain.model.entities.Notifications;

public class LoadNotificationsService implements LoadNotificationsUseCase {
    private final LoadNotificationsPort loadNotificationsPort;
    private final NotificationMapper notificationMapper;

    public LoadNotificationsService(LoadNotificationsPort loadNotificationsPort,
            NotificationMapper notificationMapper) {
        this.loadNotificationsPort = loadNotificationsPort;
        this.notificationMapper = notificationMapper;
    }

    @Override
    public NotificationsResult readAll() {
        List<Notifications> notifications = loadNotificationsPort.findAll();
        return new NotificationsResult(
                notifications.stream().map(notificationMapper::toResult).toList());
    }
}
