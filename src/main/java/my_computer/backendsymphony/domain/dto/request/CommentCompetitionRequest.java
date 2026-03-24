package my_computer.backendsymphony.domain.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import my_computer.backendsymphony.constant.ErrorMessage;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CommentCompetitionRequest {

    @NotBlank(message = ErrorMessage.Validation.NOT_BLANK)
    private String content;
}
