package my_computer.backendsymphony.domain.dto.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import lombok.experimental.FieldDefaults;
import my_computer.backendsymphony.constant.ErrorMessage;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AddMembersRequest {
    @NotEmpty(message = ErrorMessage.Validation.NOT_EMPTY)
    List<String> memberIds;
}
