package com.example.digital_payment.notification.application.config;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.digital_payment.notification.application.mapper.NotificationMapper;
import com.example.digital_payment.notification.application.port.in.GreetNotificationUserUseCase;
import com.example.digital_payment.notification.application.port.in.LoadNotificationsUseCase;
import com.example.digital_payment.notification.application.port.in.NotificationReadUseCase;
import com.example.digital_payment.notification.application.port.in.SendSseNotificationUseCase;
import com.example.digital_payment.notification.application.port.out.LoadNotificationPort;
import com.example.digital_payment.notification.application.port.out.LoadNotificationsPort;
import com.example.digital_payment.notification.application.port.out.NotificationPublisherPort;
import com.example.digital_payment.notification.application.port.out.SaveNotificationPort;
import com.example.digital_payment.notification.application.port.out.UpdateNotificationPort;
import com.example.digital_payment.notification.application.usecase.GreetNotificationService;
import com.example.digital_payment.notification.application.usecase.LoadNotificationsService;
import com.example.digital_payment.notification.application.usecase.NotificationReadService;
import com.example.digital_payment.notification.application.usecase.SendSseNotificationService;
import com.example.digital_payment.shared.application.port.out.TransactionPort;

@Configuration
public class NotificationConfig {
    @Bean
    public NotificationMapper notificationMapper() {
        return new NotificationMapper();
    }

    @Bean
    public LoadNotificationsUseCase loadNotificationsUseCase(
        LoadNotificationsPort loadNotificationsPort, NotificationMapper notificationMapper) {
        return new LoadNotificationsService(loadNotificationsPort, notificationMapper);
    }

    @Bean
    public NotificationReadUseCase notificationReadUseCase(
        LoadNotificationPort loadNotificationPort, UpdateNotificationPort updateNotificationPort,
        TransactionPort transactionPort) {
        return new NotificationReadService(loadNotificationPort, updateNotificationPort,
            transactionPort);
    }

    @Bean
    public GreetNotificationUserUseCase greetNotificationUserCase(
        SaveNotificationPort saveNotificationPort) {
        return new GreetNotificationService(saveNotificationPort);
    }

    @Bean
    public ExecutorService executorService() {
        return Executors.newVirtualThreadPerTaskExecutor();
    }

    @Bean
    SendSseNotificationUseCase sendSseNotificationUseCase(
        NotificationPublisherPort notificationPublisherPort) {
        return new SendSseNotificationService(notificationPublisherPort);
    }
}
