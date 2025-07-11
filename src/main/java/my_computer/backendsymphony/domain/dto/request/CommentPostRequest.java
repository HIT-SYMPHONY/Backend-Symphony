package my_computer.backendsymphony.domain.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import my_computer.backendsymphony.constant.ErrorMessage;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CommentPostRequest {

    @NotBlank(message = ErrorMessage.Validation.NOT_BLANK)
    private String content;

    @NotBlank(message = ErrorMessage.Validation.NOT_NULL)
    private String postId;
}
