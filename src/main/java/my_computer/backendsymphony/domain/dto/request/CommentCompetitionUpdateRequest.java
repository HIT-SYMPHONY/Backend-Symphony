package my_computer.backendsymphony.domain.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import my_computer.backendsymphony.constant.ErrorMessage;

@Getter
@Setter
public class CommentCompetitionUpdateRequest {
    @NotBlank(message = ErrorMessage.Validation.NOT_BLANK)
    private String content;
}
