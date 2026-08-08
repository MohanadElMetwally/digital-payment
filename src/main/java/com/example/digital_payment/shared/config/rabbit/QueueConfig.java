package com.example.digital_payment.shared.config.rabbit;

import lombok.Data;

@Data
public class QueueConfig {
    private String name;
    private String routingKey;
    private String exchange;
    private String dlx;
    private String dlqRoutingKey;
    private Integer ttl;
}