package my_computer.backendsymphony.domain.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.*;
import my_computer.backendsymphony.constant.ErrorMessage;
import my_computer.backendsymphony.constant.Gender;
import my_computer.backendsymphony.constant.Role;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserUpdateRequest {

    private String firstName;

    private String lastName;

    @Size(min = 10, max = 10, message = ErrorMessage.Validation.INVALID_STUDENT_CODE)
    private String studentCode;

    private String phoneNumber;

    private Gender gender;

    private LocalDate dateBirth;

    @Email(message = ErrorMessage.Validation.INVALID_FORMAT_FIELD)
    private String email;

    private String intake;

    private String username;
}
