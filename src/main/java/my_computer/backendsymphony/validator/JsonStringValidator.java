package my_computer.backendsymphony.validator;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import my_computer.backendsymphony.validator.annotation.IsJsonString;


public class JsonStringValidator implements ConstraintValidator<IsJsonString, String> {
    private static final ObjectMapper objectMapper = new ObjectMapper();
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.trim().isEmpty()) {
            return true;
        }

        try {
            objectMapper.readTree(value);
        } catch (JsonProcessingException e) {
            return false;
        }

        return true;
    }
}
