package my_computer.backendsymphony.domain.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserSummaryResponse {
    String id;
    String username;
    String fullName;
    String intake;
    String imageUrl;
    String lastLogin;
}
