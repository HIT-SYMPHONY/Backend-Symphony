package my_computer.backendsymphony.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter

public class NotFoundException extends RuntimeException {

  private final HttpStatus status;

  public NotFoundException(String message) {
    super(message); // dùng message của RuntimeException
    this.status = HttpStatus.NOT_FOUND;
  }

  public NotFoundException(HttpStatus status, String message) {
    super(message);
    this.status = status;
  }
}
