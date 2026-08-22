package com.example.digital_payment.shared.dto;

import com.example.digital_payment.shared.domain.enums.SseNotificationType;

public record NotificationMessage<T>(SseNotificationType type, T payload) {

}
