package my_computer.backendsymphony.domain.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import my_computer.backendsymphony.constant.ErrorMessage;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class PostRequest {

    @NotBlank(message = ErrorMessage.Validation.NOT_BLANK)
    private String title;

    @NotBlank(message = ErrorMessage.Validation.NOT_BLANK)
    private String content;

    @NotNull(message = ErrorMessage.Validation.NOT_NULL)
    private LocalDateTime deadline;

    @NotBlank(message = ErrorMessage.Validation.NOT_BLANK)
    private String classRoomId;


}
