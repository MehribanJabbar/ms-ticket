package az.ingress.msticket.model.response;

import az.ingress.msticket.model.enums.OrderStatus;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderResponse {
    Long id;
    Long userId;
    String userFinCode;
    String orderNumber;
    OrderStatus status;
    String orderDetails;
}
