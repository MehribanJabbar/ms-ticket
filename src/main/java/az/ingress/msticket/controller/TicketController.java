package az.ingress.msticket.controller;

import az.ingress.msticket.dto.request.SaveTicketRequest;
import az.ingress.msticket.dto.response.TicketResponse;
import az.ingress.msticket.enums.TicketStatus;
import az.ingress.msticket.model.response.OrderResponse;
import az.ingress.msticket.service.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequiredArgsConstructor
@RequestMapping("v1/tickets")
public class TicketController {
    private final TicketService ticketService;

    @GetMapping("/{id}")
    @ResponseStatus(OK)
    public TicketResponse getTicketById(@PathVariable Long id) {
        return ticketService.getTicketById(id);
    }

    @GetMapping("/status")
    @ResponseStatus(OK)
    public TicketResponse getTicketById(@RequestBody TicketStatus ticketStatus) {
        return ticketService.getTicketByStatus(ticketStatus);
    }

    @GetMapping("/{id}/order")
    @ResponseStatus(OK)
    public OrderResponse getOrderByOrderId(@PathVariable Long orderId) {
        return ticketService.getOrderByOrderId(orderId);
    }

    @PostMapping
    @ResponseStatus(CREATED)
    public void saveTicket(@RequestBody SaveTicketRequest request) {
        ticketService.saveTicket(request);
    }

    @PutMapping("/{id}/status")
    @ResponseStatus(OK)
    public void changeStatus(@PathVariable Long id, @RequestBody TicketStatus status) {
        ticketService.changeStatus(id, status);
    }

}
