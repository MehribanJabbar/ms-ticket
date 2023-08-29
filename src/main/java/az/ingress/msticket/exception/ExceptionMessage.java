package az.ingress.msticket.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ExceptionMessage {

    UNEXPECTED_ERROR("UNEXPECTED_ERROR", "Unexpected error occurred"),
    TICKET_NOT_FOUND("TICKET_NOT_FOUND", "Ticket not found");

    private final String code;
    private final String message;

}
