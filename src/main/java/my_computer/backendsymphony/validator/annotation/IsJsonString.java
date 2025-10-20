package my_computer.backendsymphony.validator.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import my_computer.backendsymphony.constant.ErrorMessage;
import my_computer.backendsymphony.validator.JsonStringValidator;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Constraint(validatedBy = JsonStringValidator.class)
@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface IsJsonString {
    String message() default ErrorMessage.Validation.MUST_BE_JSON_STRING;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
