package my_computer.backendsymphony.domain.dto.request;


import jakarta.validation.constraints.NotBlank;
import lombok.*;
import my_computer.backendsymphony.constant.ErrorMessage;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommentCompetitionRequest {

    private Double score;

    @NotBlank(message =  ErrorMessage.Validation.NOT_BLANK)
    private String content;

    @NotBlank(message = ErrorMessage.Validation.NOT_BLANK)
    private String competitionId;
}
