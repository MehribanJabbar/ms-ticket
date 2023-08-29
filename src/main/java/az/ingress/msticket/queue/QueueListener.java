package az.ingress.msticket.queue;

import az.ingress.msticket.dao.entity.TicketEntity;
import az.ingress.msticket.model.response.OrderResponse;
import az.ingress.msticket.service.TicketService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class QueueListener {
    private final TicketService ticketService;

    @SneakyThrows
    @RabbitListener(queues = "${rabbitmq.queue.order}")
    public void consume(Long id){
        ticketService.getTicketById(id);
    }

}
