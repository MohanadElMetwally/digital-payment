package com.example.digital_payment.settlement.infrastructure.messaging;

import java.util.UUID;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;
import com.example.digital_payment.settlement.application.port.out.SettlementPublisherPort;

@Component
class SettlementRabbitPublisher implements SettlementPublisherPort {

    private final RabbitTemplate rabbitTemplate;
    private static final String EXCHANGE = "settlement.exchange";
    private static final String ROUTING_KEY = "settlement.process";

    public SettlementRabbitPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void publish(UUID id) {
        rabbitTemplate.convertAndSend(EXCHANGE, ROUTING_KEY, id);
    }
}
