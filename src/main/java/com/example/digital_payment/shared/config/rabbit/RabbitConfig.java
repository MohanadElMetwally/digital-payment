package com.example.digital_payment.shared.config.rabbit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Declarable;
import org.springframework.amqp.core.Declarables;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(RabbitMqProperties.class)
public class RabbitConfig {
    private final RabbitMqProperties props;

    public RabbitConfig(RabbitMqProperties props) {
        this.props = props;
    }

    @Bean
    public Declarables rabbitDeclarables() {
        List<Declarable> declarables = new ArrayList<>();

        Map<String, Exchange> exchangeMap = new HashMap<>();
        for (ExchangeConfig ec : props.getExchanges()) {
            TopicExchange exchange = new TopicExchange(ec.getName(), true, false);
            exchangeMap.put(ec.getName(), exchange);
            declarables.add(exchange);
        }

        for (QueueConfig qc : props.getQueues()) {
            QueueBuilder builder = QueueBuilder.durable(qc.getName());
            if (qc.getDlx() != null) {
                builder.withArgument("x-dead-letter-exchange", qc.getDlx())
                    .withArgument("x-dead-letter-routing-key", qc.getDlqRoutingKey());
            }
            if (qc.getTtl() != null) {
                builder.withArgument("x-message-ttl", qc.getTtl());
            }
            Queue queue = builder.build();
            declarables.add(queue);

            Exchange exchange = exchangeMap.get(qc.getExchange());
            declarables.add(
                BindingBuilder.bind(queue).to((TopicExchange) exchange).with(qc.getRoutingKey()));
        }

        return new Declarables(declarables);
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory,
        MessageConverter jsonMessageConverter) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(jsonMessageConverter);
        return template;
    }
}
