package my_computer.backendsymphony.domain.dto.request;

import jakarta.validation.constraints.NotBlank;
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
public class UpdateRoleRequest {

    @NotBlank(message = ErrorMessage.Validation.NOT_BLANK)
    String roleStr;

    @NotBlank(message = ErrorMessage.Validation.NOT_BLANK)
    List<String> usersId;
}
