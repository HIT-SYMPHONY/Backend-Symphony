package my_computer.backendsymphony.domain.dto.request;

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
public class ClassroomUpdateRequest {

    String name;

    LocalDate startTime;

    @Positive(message = ErrorMessage.Validation.POSITIVE)
    Integer duration;

    String description;

    String leaderId;
}
