package com.example.digital_payment.shared.application.port.out;

public interface EventPublisherPort {
    void publish(Object event);
}
