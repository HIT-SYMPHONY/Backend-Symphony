package my_computer.backendsymphony.domain.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import my_computer.backendsymphony.constant.ErrorMessage;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class AddMembersToCompetitionRequest {
    @NotBlank(message = ErrorMessage.Validation.NOT_BLANK)
    private String competitionId;

    @NotEmpty(message = ErrorMessage.Validation.NOT_EMPTY)
    private List<String> userIds;
}
