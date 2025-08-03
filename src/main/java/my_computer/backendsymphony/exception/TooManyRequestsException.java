package my_computer.backendsymphony.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class TooManyRequestsException extends RuntimeException {

    private final HttpStatus status = HttpStatus.TOO_MANY_REQUESTS; // 429

    public TooManyRequestsException(String message, Object... params) {
        super(String.format(message, params));
    }

    public TooManyRequestsException(String message) {
        super(message);
    }
}
