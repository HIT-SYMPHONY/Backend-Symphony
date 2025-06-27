package my_computer.backendsymphony.domain.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
public class VerifyCodeRequest {
    @NotBlank
    private String email;

    @NotBlank
    private String tempPassword;
}
