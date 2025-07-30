package my_computer.backendsymphony.domain.dto.request;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import my_computer.backendsymphony.constant.ErrorMessage;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MarkRequest {

    @NotNull(message = ErrorMessage.Validation.NOT_NULL)
    private String id;

    @NotNull(message = ErrorMessage.Validation.NOT_NULL)
    @DecimalMin(value = "0.0", message = ErrorMessage.Validation.INVALID_SCORE)
    @DecimalMax(value = "10.0", message = ErrorMessage.Validation.INVALID_SCORE)
    private Double score;
}
