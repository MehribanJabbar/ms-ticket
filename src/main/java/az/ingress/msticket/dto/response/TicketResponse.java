package az.ingress.msticket.dto.response;

import az.ingress.msticket.enums.TicketStatus;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TicketResponse {
    Long id;
    String ticketNumber;
    String trackingCode;
    Long orderId;
    LocalDateTime createdAt;
    TicketStatus ticketStatus;
    String ticketDetails;
}
