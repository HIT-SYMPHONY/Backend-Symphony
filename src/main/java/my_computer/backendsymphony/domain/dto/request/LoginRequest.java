package my_computer.backendsymphony.domain.dto.request;


import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import my_computer.backendsymphony.constant.ErrorMessage;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LoginRequest {
    @NotBlank(message = ErrorMessage.Validation.NOT_BLANK)
    String studentCode;

    @NotBlank(message = ErrorMessage.Validation.NOT_BLANK)
    String password;
}