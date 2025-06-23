package my_computer.backendsymphony.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class NotFoundException extends RuntimeException {

    private final HttpStatus status = HttpStatus.NOT_FOUND;

    public NotFoundException(String message, String[] params) {
        super(String.format(message, (Object[]) params));
    }

    public NotFoundException(String message) {
        super(message);
    }
}