package com.example.digital_payment.shared.infrastructure.persistence.adapters;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import com.example.digital_payment.shared.application.port.out.EventPublisherPort;

@Component
public class EventPublisherAdapter implements EventPublisherPort {
    private final ApplicationEventPublisher publisher;

    public EventPublisherAdapter(ApplicationEventPublisher publisher) {
        this.publisher = publisher;
    }

    @Override
    public void publish(Object event) {
        publisher.publishEvent(event);
    }

}
