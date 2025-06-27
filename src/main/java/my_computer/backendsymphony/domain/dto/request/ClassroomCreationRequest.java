package my_computer.backendsymphony.domain.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;
import my_computer.backendsymphony.constant.ErrorMessage;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ClassroomCreationRequest {
    @NotBlank(message = ErrorMessage.Validation.NOT_BLANK)
    String name;
    LocalDateTime startTime;
    Integer duration;
    @NotBlank(message = ErrorMessage.Validation.NOT_BLANK)
    String leaderId;
}
