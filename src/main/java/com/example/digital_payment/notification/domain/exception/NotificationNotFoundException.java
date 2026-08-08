package com.example.digital_payment.notification.domain.exception;

import com.example.digital_payment.shared.exception.ResourceNotFoundException;

public class NotificationNotFoundException extends ResourceNotFoundException {
    public NotificationNotFoundException(String message) {
        super(message);
    }
}
