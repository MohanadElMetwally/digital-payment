package com.example.digital_payment.notification.infrastructure.persistence.adapter;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.example.digital_payment.notification.application.port.out.LoadNotificationPort;
import com.example.digital_payment.notification.application.port.out.LoadNotificationsPort;
import com.example.digital_payment.notification.application.port.out.SaveNotificationPort;
import com.example.digital_payment.notification.application.port.out.UpdateNotificationPort;
import com.example.digital_payment.notification.domain.model.entities.Notifications;
import com.example.digital_payment.notification.infrastructure.persistence.entity.NotificationEntity;
import com.example.digital_payment.notification.infrastructure.persistence.mapper.NotificationPersistenceMapper;
import com.example.digital_payment.notification.infrastructure.persistence.repository.NotificationJpaRepository;

@Component
public class NotificationPersistenceAdapter implements SaveNotificationPort, LoadNotificationsPort,
    LoadNotificationPort, UpdateNotificationPort {
    private final NotificationJpaRepository notificationJpaRepository;
    private final NotificationPersistenceMapper notificationPersistenceMapper;

    @Override
    @Transactional
    public void save(Notifications notification) {
        NotificationEntity entity = notificationPersistenceMapper.toEntity(notification);
        notificationJpaRepository.save(entity);
    }

    public NotificationPersistenceAdapter(NotificationJpaRepository notificationJpaRepository,
        NotificationPersistenceMapper notificationPersistenceMapper) {
        this.notificationJpaRepository = notificationJpaRepository;
        this.notificationPersistenceMapper = notificationPersistenceMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Notifications> findAll() {
        return notificationJpaRepository.findAll()
            .stream()
            .map(notificationPersistenceMapper::toDomain)
            .toList();
    }

    @Override
    public Optional<Notifications> findById(UUID id) {
        return notificationJpaRepository.findById(id).map(notificationPersistenceMapper::toDomain);
    }

    @Override
    @Transactional
    public void UpdateNotification(UUID id, Notifications notification) {
        NotificationEntity entity = notificationJpaRepository.getReferenceById(id);
        notificationPersistenceMapper.UpdateEntity(entity, notification);
    }

}
