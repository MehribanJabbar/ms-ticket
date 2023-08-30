package az.ingress.msticket.service;

import az.ingress.msticket.dao.entity.TicketEntity;
import az.ingress.msticket.dao.repository.TicketRepository;
import az.ingress.msticket.dto.request.SaveTicketRequest;
import az.ingress.msticket.dto.response.TicketResponse;
import az.ingress.msticket.enums.TicketStatus;
import az.ingress.msticket.exception.NotFoundException;
import az.ingress.msticket.mapper.TicketMapper;
import az.ingress.msticket.model.response.OrderResponse;
import az.ingress.msticket.queue.QueueSender;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TicketService {
    private final TicketRepository ticketRepository;
    private final QueueSender queueSender;

    public TicketResponse getTicketById(Long id) {
        var ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("TICKET_NOT_FOUND"));
        return TicketMapper.buildToResponse(ticket);
    }

    public TicketResponse getTicketByStatus(TicketStatus status) {
        return TicketMapper.buildToResponse(ticketRepository.findByTicketStatus(status));
    }

    public void saveTicket(SaveTicketRequest request) {
        TicketEntity ticket = TicketMapper.buildToEntity(request);
        ticket.setTicketStatus(TicketStatus.WAITING);
        ticketRepository.save(ticket);
    }

    public void changeStatus(Long id) {
        var ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("TICKET_NOT_FOUND"));
        ticket.setTicketStatus(TicketStatus.DONE);
        ticketRepository.save(ticket);
        queueSender.sendToCardQueue(ticket.getOrderId());
    }
}
