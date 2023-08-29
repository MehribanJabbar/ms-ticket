package az.ingress.msticket.dto.request;

import az.ingress.msticket.enums.TicketStatus;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateTicketRequest {
    String ticketDetails;
    TicketStatus ticketStatus;
    Long orderId;
}
