package my_computer.backendsymphony.domain.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import my_computer.backendsymphony.constant.ErrorMessage;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class NotificationRequest {

    @NotNull(message = ErrorMessage.Validation.NOT_BLANK)
    private String content;

    private String classRoomId;

    private String competitionId;
}
