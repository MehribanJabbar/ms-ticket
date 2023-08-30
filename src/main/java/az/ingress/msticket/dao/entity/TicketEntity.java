package az.ingress.msticket.dao.entity;

import az.ingress.msticket.enums.TicketStatus;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tickets")
public class TicketEntity {
    @Id
    @GeneratedValue
    private Long id;

    private String trackingCode ;
    private Long orderId;

    @CreationTimestamp
    private LocalDateTime createdAt ;
    private String ticketDetails;

    @Enumerated(EnumType.STRING)
    private TicketStatus ticketStatus ;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TicketEntity that = (TicketEntity) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
