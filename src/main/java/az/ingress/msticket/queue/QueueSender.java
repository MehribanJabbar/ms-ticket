package az.ingress.msticket.queue;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class QueueSender {
    private final ObjectMapper objectMapper;
    private final RabbitTemplate rabbitTemplate;

    @SneakyThrows
    public void sendToCardQueue(Long orderId) {
        rabbitTemplate.convertAndSend("ORDER_Q", objectMapper.writeValueAsString(orderId));
    }

}
