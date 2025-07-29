package my_computer.backendsymphony.domain.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.*;
import lombok.experimental.FieldDefaults;
import my_computer.backendsymphony.constant.ErrorMessage;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ClassroomCreationRequest {

    @NotBlank(message = ErrorMessage.Validation.NOT_BLANK)
    String name;

    LocalDate startTime;

    @Positive(message = ErrorMessage.Validation.POSITIVE)
    Integer duration;

    private String description;

    @NotBlank(message = ErrorMessage.Validation.NOT_BLANK)
    String leaderId;
}
