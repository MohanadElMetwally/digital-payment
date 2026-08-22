package com.example.digital_payment.notification.api.facade;

import java.util.UUID;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import com.example.digital_payment.notification.api.dto.NotificationsResponse;
import com.example.digital_payment.notification.api.mapper.NotificationApiMapper;
import com.example.digital_payment.notification.application.dto.NotificationsResult;
import com.example.digital_payment.notification.application.port.in.LoadNotificationsUseCase;
import com.example.digital_payment.notification.application.port.in.NotificationReadUseCase;
import com.example.digital_payment.notification.infrastructure.sse.SseConnection;
import com.example.digital_payment.shared.security.CurrentUserContext;

@Component
public class NotificationFacade {
    private final LoadNotificationsUseCase loadNotificationsUseCase;
    private final NotificationReadUseCase notificationReadUseCase;
    private final NotificationApiMapper notificationApiMapper;
    private final SseConnection sseConnection;
    private final CurrentUserContext currentUserContext;

    public NotificationFacade(LoadNotificationsUseCase loadNotificationsUseCase,
            NotificationReadUseCase notificationReadUseCase, SseConnection sseConnection,
            CurrentUserContext currentUserContext, NotificationApiMapper notificationApiMapper) {
        this.loadNotificationsUseCase = loadNotificationsUseCase;
        this.notificationReadUseCase = notificationReadUseCase;
        this.notificationApiMapper = notificationApiMapper;
        this.currentUserContext = currentUserContext;
        this.sseConnection = sseConnection;
    }

    public NotificationsResponse readAll() {
        NotificationsResult result = loadNotificationsUseCase.readAll();
        return notificationApiMapper.toResponseList(result);
    }

    public void notificationRead(UUID id) {
        notificationReadUseCase.NotificationRead(id);
    }

    public SseEmitter connect() {
        return sseConnection.connect(currentUserContext.getUserId());
    }
}
