package com.example.digital_payment.notification.api.controller;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.example.digital_payment.notification.api.dto.NotificationsResponse;
import com.example.digital_payment.notification.api.facade.NotificationFacade;

@RestController
@RequestMapping("/notifications")
public class NotificationController {
    private final NotificationFacade notificationFacade;

    public NotificationController(NotificationFacade notificationFacade) {
        this.notificationFacade = notificationFacade;
    }

    @GetMapping
    public ResponseEntity<NotificationsResponse> readAll() {
        return ResponseEntity.status(HttpStatus.OK).body(notificationFacade.readAll());
    }

    @GetMapping(value = "/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter stream() {
        return notificationFacade.connect();
    }

    @PatchMapping("/{id}/read")
    public ResponseEntity<Void> notificationRead(@PathVariable UUID id) {
        notificationFacade.notificationRead(id);
        return ResponseEntity.noContent().build();
    }

}
