package my_computer.backendsymphony.domain.dto.request;

import jakarta.validation.constraints.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import my_computer.backendsymphony.constant.ErrorMessage;
import my_computer.backendsymphony.constant.Gender;
import my_computer.backendsymphony.constant.Role;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserCreationRequest {
    @NotBlank(message = ErrorMessage.Validation.NOT_BLANK)
    String username;
    @NotBlank(message = ErrorMessage.Validation.NOT_BLANK)
    @Email(message = ErrorMessage.Validation.INVALID_FORMAT_FIELD)
    String email;
    @NotBlank(message = ErrorMessage.Validation.NOT_BLANK)
    @Pattern(
            regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{6,}$",
            message = ErrorMessage.Validation.INVALID_FORMAT_PASSWORD
    )
    String password;
    @NotBlank(message = ErrorMessage.Validation.NOT_BLANK)
    String firstName;
    @NotBlank(message = ErrorMessage.Validation.NOT_BLANK)
    String lastName;
    @NotNull(message = ErrorMessage.Validation.NOT_NULL)
    Role role;
    @NotBlank(message = ErrorMessage.Validation.NOT_BLANK)
    @Size(min = 10, max = 10, message = ErrorMessage.Validation.INVALID_STUDENT_CODE)
    String studentCode;
    @NotBlank(message = ErrorMessage.Validation.NOT_BLANK)
    String phoneNumber;
    @NotNull(message = ErrorMessage.Validation.NOT_NULL)
    @Past(message = ErrorMessage.Validation.INVALID_DATE_PAST)
    LocalDate dateBirth;
    @NotNull(message = ErrorMessage.Validation.NOT_NULL)
    Gender gender;
    @NotBlank(message = ErrorMessage.Validation.NOT_BLANK)
    String intake;
}