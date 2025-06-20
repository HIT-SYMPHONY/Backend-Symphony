package my_computer.backendsymphony.exception;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import my_computer.backendsymphony.base.RestData;
import my_computer.backendsymphony.base.VsResponseUtil;
import my_computer.backendsymphony.constant.ErrorMessage;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.LinkedHashMap;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@RestControllerAdvice
public class GlobalExceptionHandler {
    private final MessageSource messageSource;

    @ExceptionHandler(BindException.class)
    public ResponseEntity<RestData<?>> handleValidationException(BindException ex) {
        Map<String, String> errors = new LinkedHashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> {
            String fieldName = error.getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        log.warn("Validation failed for request body: {}", errors);
        return VsResponseUtil.error(HttpStatus.BAD_REQUEST, errors);
    }

    @ExceptionHandler(DuplicateResourceException.class)
    public ResponseEntity<RestData<?>> handleDuplicateResourceException(DuplicateResourceException ex) {
        String resolvedMessage = messageSource.getMessage(
                ex.getMessage(),
                ex.getParams(),
                LocaleContextHolder.getLocale()
        );

        HttpStatus status = ex.getStatus();
        log.error("DuplicateResourceException: Status={}, Message='{}'", status, resolvedMessage, ex);
        return VsResponseUtil.error(status, resolvedMessage);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<RestData<?>> handleNotFoundException(NotFoundException ex) {
        String resolvedMessage = messageSource.getMessage(
                ex.getMessage(),
                ex.getParams(),
                LocaleContextHolder.getLocale()
        );
        HttpStatus status = ex.getStatus();
        log.error("NotFoundException: Status={}, Message='{}'", status, resolvedMessage, ex);
        return VsResponseUtil.error(status, resolvedMessage);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<RestData<?>> handleUncategorizedException(Exception ex) {
        log.error(ex.getMessage(), ex);
        String message = messageSource.getMessage(ErrorMessage.ERR_EXCEPTION_GENERAL, null,
                LocaleContextHolder.getLocale());
        return VsResponseUtil.error(HttpStatus.INTERNAL_SERVER_ERROR, message);
    }
}
