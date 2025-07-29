package my_computer.backendsymphony.domain.dto.request;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;
import my_computer.backendsymphony.constant.ErrorMessage;

import java.time.LocalDateTime;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CompetitionRequest {
    @NotBlank(message = ErrorMessage.Validation.NOT_BLANK)
    String name;

    String rule;

    String description;

    @NotBlank(message = ErrorMessage.Validation.NOT_BLANK)
    String content;

    @NotNull(message = ErrorMessage.Validation.NOT_NULL)
    String competitionLeaderId;

    @NotNull(message = ErrorMessage.Validation.NOT_NULL)
    @Future(message = ErrorMessage.Validation.MUST_IN_FUTURE)
    LocalDateTime startTime;

    @NotNull(message = ErrorMessage.Validation.NOT_NULL)
    @Future(message = ErrorMessage.Validation.MUST_IN_FUTURE)
    LocalDateTime endTime;
}