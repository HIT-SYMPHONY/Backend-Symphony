package my_computer.backendsymphony.domain.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import my_computer.backendsymphony.constant.ErrorMessage;

import java.time.DayOfWeek;
import java.time.LocalTime;

@Data
public class LessonCreationRequest {
    @NotBlank(message = ErrorMessage.Validation.NOT_BLANK)
    private String content;

    @NotBlank(message = ErrorMessage.Validation.NOT_BLANK)
    private String title;

    @NotBlank(message = ErrorMessage.Validation.NOT_BLANK)
    private String location;

    @NotNull(message = ErrorMessage.Validation.NOT_NULL)
    private LocalTime startTime;

    @NotNull(message = ErrorMessage.Validation.NOT_NULL)
    private LocalTime endTime;

    @NotNull(message = ErrorMessage.Validation.NOT_NULL)
    private DayOfWeek dayOfWeek;

    @NotBlank(message = ErrorMessage.Validation.NOT_BLANK)
    private String classRoomId;
}
