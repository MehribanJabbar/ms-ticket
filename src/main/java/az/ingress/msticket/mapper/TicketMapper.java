package az.ingress.msticket.mapper;

import az.ingress.msticket.dto.request.SaveTicketRequest;
import az.ingress.msticket.dto.response.TicketResponse;
import az.ingress.msticket.dao.entity.TicketEntity;
import az.ingress.msticket.dto.request.UpdateTicketRequest;

public class TicketMapper {
    public static TicketResponse buildToResponse(TicketEntity ticket){
        return TicketResponse.builder()
                .id(ticket.getId())
                .trackingCode(ticket.getTrackingCode())
                .orderId(ticket.getOrderId())
                .createdAt(ticket.getCreatedAt())
                .ticketStatus(ticket.getTicketStatus())
                .ticketDetails(ticket.getTicketDetails())
                .build();
    }

    public static TicketEntity buildToEntity(SaveTicketRequest request){
        return TicketEntity.builder()
                .orderId(request.getOrderId())
                .ticketDetails(request.getTicketDetails())
                .trackingCode(request.getTrackingCode())
                .build();
    }
}
