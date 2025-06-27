package my_computer.backendsymphony.domain.dto.request;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;
import my_computer.backendsymphony.constant.ErrorMessage;
import my_computer.backendsymphony.constant.Gender;
import my_computer.backendsymphony.constant.Role;
import my_computer.backendsymphony.domain.entity.ChatRoomUser;
import my_computer.backendsymphony.domain.entity.ClassRoom;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserUpdateRequest {

    private String firstName;

    private String lastName;

    private String studentCode;

    private String password;

    private String phoneNumber;

    private Role role;

    private String imageUrl;

    private Gender gender;

    private LocalDate dateBirth;

    private String email;

    private String intake;
}
