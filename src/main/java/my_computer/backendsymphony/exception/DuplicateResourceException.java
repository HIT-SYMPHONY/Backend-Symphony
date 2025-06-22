package my_computer.backendsymphony.exception;


import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class DuplicateResourceException extends RuntimeException {
    private HttpStatus status;
    private String[] params;

    public DuplicateResourceException(String message) {
        super(message);
        this.status = HttpStatus.CONFLICT;
        this.params = null;
    }

    public DuplicateResourceException(HttpStatus status, String message) {
        super(message);
        this.status = status;
        this.params = null;
    }

    public DuplicateResourceException(String message, String[] params) {
        super(message);
        this.status = HttpStatus.CONFLICT;
        this.params = params;
    }

    public DuplicateResourceException(HttpStatus status, String message, String[] params) {
        super(message);
        this.status = status;
        this.params = params;
    }

}