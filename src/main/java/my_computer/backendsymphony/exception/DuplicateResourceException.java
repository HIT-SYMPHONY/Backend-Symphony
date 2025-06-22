package my_computer.backendsymphony.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class DuplicateResourceException extends RuntimeException {

    private final HttpStatus status = HttpStatus.CONFLICT;

    public DuplicateResourceException(String message, String[] params) {
        super(String.format(message, (Object[]) params));
    }

    public DuplicateResourceException(String message) {
        super(message);
    }
}