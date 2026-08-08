package com.example.digital_payment.shared.config.rabbit;

import lombok.Data;

@Data
public class ExchangeConfig {
    private String name;
    private String type;
    private boolean durable = true;
    private boolean autoDelete = false;
}
