package my_computer.backendsymphony.domain.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;
import my_computer.backendsymphony.constant.Gender;
import my_computer.backendsymphony.constant.Role;

import java.time.LocalDate;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserResponse {
    String id;
    String username;
    String email;
    String studentCode;
    String firstName;
    String lastName;
    String fullName;
    String phoneNumber;
    LocalDate dateBirth;
    Gender gender;
    String intake;
    Role role;
    String imageUrl;
    LocalDateTime lastLogin;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
}
