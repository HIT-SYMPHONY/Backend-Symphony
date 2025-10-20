package my_computer.backendsymphony.domain.dto.request;

import jakarta.validation.constraints.Positive;
import lombok.*;
import lombok.experimental.FieldDefaults;
import my_computer.backendsymphony.constant.ErrorMessage;
import my_computer.backendsymphony.validator.annotation.IsJsonString;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ClassroomUpdateRequest {

    String name;

    LocalDate startTime;

    String timeSlot;

    LocalDate endTime;

    @Positive(message = ErrorMessage.Validation.POSITIVE)
    Integer duration;

    @IsJsonString
    String description;

    String leaderId;
}
