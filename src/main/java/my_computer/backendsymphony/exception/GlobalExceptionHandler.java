package my_computer.backendsymphony.exception;

import lombok.extern.slf4j.Slf4j;
import my_computer.backendsymphony.base.RestData;
import my_computer.backendsymphony.base.VsResponseUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(NotFoundException.class)
  public ResponseEntity<RestData<?>> handleUploadImageException(NotFoundException ex) {
    log.error(ex.getMessage(), ex);
    return VsResponseUtil.error(ex.getStatus(), ex.getMessage());
  }
}