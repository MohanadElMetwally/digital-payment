package com.example.digital_payment.identity.application.port.out;

public interface EventPublisherPort {
    void publish(Object event);
}
