package my_computer.backendsymphony.domain.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
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

    @Positive(message = ErrorMessage.Validation.POSITIVE)
    private Double score;

    @NotBlank(message = ErrorMessage.Validation.NOT_BLANK)
    private String content;

    @NotNull(message = ErrorMessage.Validation.NOT_NULL)
    private String competitionId;
}
