package az.ingress.msticket.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static az.ingress.msticket.exception.ExceptionMessage.TICKET_NOT_FOUND;
import static az.ingress.msticket.exception.ExceptionMessage.UNEXPECTED_ERROR;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Slf4j
@RestControllerAdvice
public class ErrorHandler {

    @ExceptionHandler(Exception.class)
    @ResponseStatus(INTERNAL_SERVER_ERROR)
    public ErrorResponse handle(Exception exception){
        log.error("Exception", exception);
        return new ErrorResponse(UNEXPECTED_ERROR.getCode(), UNEXPECTED_ERROR.getMessage());
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(NOT_FOUND)
    public ErrorResponse handle(NotFoundException exception){
        log.error("NotFoundException", exception);
        return new ErrorResponse(TICKET_NOT_FOUND.getCode(), TICKET_NOT_FOUND.getMessage());
    }

}
