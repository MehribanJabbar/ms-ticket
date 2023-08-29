package az.ingress.msticket.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
    private final String orderQ;
    private final String orderDLQ;
    private final String orderQExchange;
    private final String orderDLQExchange;
    private final String orderQKey;
    private final String orderDLQKey;

    public RabbitMQConfig(@Value("${rabbitmq.queue.order}") String orderQ,
                          @Value("${rabbitmq.queue.order-dlq}") String orderDLQ) {
        this.orderQ = orderQ;
        this.orderDLQ = orderDLQ;
        this.orderQExchange = orderQ + "_Exchange";
        this.orderDLQExchange = orderDLQ + "_Exchange";
        this.orderQKey = orderQ + "_Key";
        this.orderDLQKey = orderDLQ + "_Key";
    }

    @Bean
    DirectExchange orderDLQExchange() {
        return new DirectExchange(orderDLQExchange);
    }

    @Bean
    DirectExchange orderQExchange() {
        return new DirectExchange(orderQExchange);
    }

    @Bean
    Queue orderDLQ() {
        return QueueBuilder.durable(orderDLQ).build();
    }

    @Bean
    Queue orderQ() {
        return QueueBuilder.durable(orderQ)
                .withArgument("x-dead-letter-exchange", orderDLQExchange)
                .withArgument("x-dead-letter-routing-key", orderDLQKey)
                .build();
    }

    @Bean
    Binding orderDLQBinding() {
        return BindingBuilder.bind(orderDLQ())
                .to(orderDLQExchange()).with(orderDLQKey);
    }

    @Bean
    Binding orderQBinding() {
        return BindingBuilder.bind(orderQ())
                .to(orderQExchange()).with(orderQKey);
    }
}
