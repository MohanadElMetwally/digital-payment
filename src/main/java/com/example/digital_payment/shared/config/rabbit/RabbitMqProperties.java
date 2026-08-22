package com.example.digital_payment.shared.config.rabbit;

import java.util.ArrayList;
import java.util.List;
import org.springframework.boot.context.properties.ConfigurationProperties;
import lombok.Data;

@Data
@ConfigurationProperties(prefix = "app.rabbitmq")
public class RabbitMqProperties {
    private List<ExchangeConfig> exchanges = new ArrayList<>();
    private List<QueueConfig> queues = new ArrayList<>();
}
