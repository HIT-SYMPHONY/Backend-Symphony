package my_computer.backendsymphony.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AuthRequest {

    @NotBlank(message = "Email cannot blank!")
    @Email(message = "Invalid email format!")
    private String email;
}
