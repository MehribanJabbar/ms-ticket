package az.ingress.msticket.dao.repository;

import az.ingress.msticket.dao.entity.TicketEntity;
import az.ingress.msticket.enums.TicketStatus;
import az.ingress.msticket.model.response.OrderResponse;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketRepository extends JpaRepository<TicketEntity, Long> {
    TicketEntity findByTicketStatus(TicketStatus ticketStatus);

    OrderResponse findByOrderId(Long orderId);
}
