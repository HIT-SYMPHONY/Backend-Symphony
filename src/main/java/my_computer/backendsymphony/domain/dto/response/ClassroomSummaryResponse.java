package my_computer.backendsymphony.domain.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ClassroomSummaryResponse {
    String id;
    String name;
    String image;
    String leaderId;
    String leaderName;
    Integer numberOfPosts;
}
