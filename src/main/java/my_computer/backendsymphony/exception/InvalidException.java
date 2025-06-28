package my_computer.backendsymphony.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class InvalidException extends RuntimeException {

    private final HttpStatus status;

    public InvalidException(String message) {
        super(message);
        this.status = HttpStatus.BAD_REQUEST;
    }

    public InvalidException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

}
