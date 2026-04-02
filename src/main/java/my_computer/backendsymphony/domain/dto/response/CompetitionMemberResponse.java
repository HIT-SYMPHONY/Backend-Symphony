package my_computer.backendsymphony.domain.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CompetitionMemberResponse {
    String id;
    String username;
    String fullName;
    String intake;
    String imageUrl;
    String studentCode;
    LocalDateTime joinedAt;
}
