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

    String password;

    String firstName;

    String lastName;

    @NotBlank(message = ErrorMessage.Validation.NOT_BLANK)
    @Size(min = 10, max = 10, message = ErrorMessage.Validation.INVALID_STUDENT_CODE)
    String studentCode;

    String phoneNumber;

    LocalDate dateBirth;

    Gender gender;

    String intake;
}