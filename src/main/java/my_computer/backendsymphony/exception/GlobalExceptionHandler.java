package my_computer.backendsymphony.exception;

import lombok.extern.slf4j.Slf4j;
import my_computer.backendsymphony.base.RestData;
import my_computer.backendsymphony.base.VsResponseUtil;
import my_computer.backendsymphony.constant.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.*;

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
        ex.getBindingResult().getFieldErrors().forEach(error -> {
            String fieldName = error.getField();
            String errorMessage;
            if (isTypeMismatchError(error)) {
                Class<?> fieldType = ex.getBindingResult().getFieldType(fieldName);
                if (fieldType != null && fieldType.isEnum()) {
                    errorMessage = String.format(
                            ErrorMessage.Validation.INVALID_ENUM_VALUE,
                            error.getRejectedValue(),
                            Arrays.toString(fieldType.getEnumConstants())
                    );
                } else {
                    errorMessage = String.format(ErrorMessage.Validation.INVALID_TYPE_VALUE, error.getRejectedValue());
                }
            } else {
                errorMessage = error.getDefaultMessage();
            }
            errors.put(fieldName, errorMessage);
        });
        return VsResponseUtil.error(HttpStatus.BAD_REQUEST, errors);
    }

    @ExceptionHandler(UploadFileException.class)
    public ResponseEntity<RestData<?>> handleUploadFileException(UploadFileException ex) {
        return VsResponseUtil.error(ex.getStatus(), ex.getMessage());
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<RestData<?>> handleAccessDeniedException(AccessDeniedException ex) {
        return VsResponseUtil.error(HttpStatus.FORBIDDEN, ErrorMessage.FORBIDDEN);
    }

    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<RestData<?>> handleForbiddenException(ForbiddenException ex) {
        return VsResponseUtil.error(ex.getStatus(), ex.getMessage());
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<RestData<?>> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        log.warn("Malformed JSON request received: {}", ex.getMessage());
        return VsResponseUtil.error(HttpStatus.BAD_REQUEST, ErrorMessage.INVALID_JSON_FORMAT);
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

    @ExceptionHandler(TooManyRequestsException.class)
    public ResponseEntity<RestData<?>> handleTooManyRequestsException(TooManyRequestsException ex) {
        return VsResponseUtil.error(ex.getStatus(), ex.getMessage());
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<RestData<?>> handleNoResourceFoundException(NoResourceFoundException ex) {
        return VsResponseUtil.error(HttpStatus.NOT_FOUND, ErrorMessage.NOT_FOUND);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<RestData<?>> handleIllegalArgumentException(IllegalArgumentException ex) {
        return VsResponseUtil.error(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<RestData<?>> handleMaxUploadSizeExceededException(MaxUploadSizeExceededException ex) {
        return VsResponseUtil.error(HttpStatus.PAYLOAD_TOO_LARGE, ErrorMessage.File.FILE_TOO_LARGE);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<RestData<?>> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException ex) {
        return VsResponseUtil.error(HttpStatus.METHOD_NOT_ALLOWED, ErrorMessage.HTTP_METHOD_NOT_SUPPORTED);
    }


    private boolean isTypeMismatchError(FieldError error) {
        for (String code : Objects.requireNonNull(error.getCodes())) {
            if (code.equals("typeMismatch")) {
                return true;
            }
        }
        return false;
    }

}