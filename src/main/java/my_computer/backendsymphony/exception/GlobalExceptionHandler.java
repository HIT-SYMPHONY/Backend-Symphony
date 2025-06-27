package my_computer.backendsymphony.exception;

import lombok.extern.slf4j.Slf4j;
import my_computer.backendsymphony.base.RestData;
import my_computer.backendsymphony.base.VsResponseUtil;
import my_computer.backendsymphony.constant.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.util.LinkedHashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<RestData<?>> handleNotFoundException(NotFoundException ex) {
        return VsResponseUtil.error(ex.getStatus(), ex.getMessage());
    }

    @ExceptionHandler(DuplicateResourceException.class)
    public ResponseEntity<RestData<?>> handleDuplicateResourceException(DuplicateResourceException ex) {
        return VsResponseUtil.error(ex.getStatus(), ex.getMessage());
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<RestData<?>> handleUnauthorizedException(UnauthorizedException ex) {
        return VsResponseUtil.error(ex.getStatus(), ex.getMessage());
    }

    @ExceptionHandler(BindException.class)
    public ResponseEntity<RestData<?>> handleValidationException(BindException ex) {
        Map<String, String> errors = new LinkedHashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage())
        );
        return VsResponseUtil.error(HttpStatus.BAD_REQUEST, errors);
    }

    @ExceptionHandler(UploadFileException.class)
    public ResponseEntity<RestData<?>> handleUploadFileException(UploadFileException ex) {
        log.error("Error upload: ", ex);
        return VsResponseUtil.error(ex.getStatus(), ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<RestData<?>> handleUncategorizedException(Exception ex) {
        log.error("An unexpected server error occurred: ", ex);
        return VsResponseUtil.error(HttpStatus.INTERNAL_SERVER_ERROR, ErrorMessage.ERR_EXCEPTION_GENERAL);
    }

    @ExceptionHandler(InvalidException.class)
    public ResponseEntity<RestData<?>> handleInvalidException(InvalidException ex) {
        return VsResponseUtil.error(ex.getStatus(), ex.getMessage());
    }

}