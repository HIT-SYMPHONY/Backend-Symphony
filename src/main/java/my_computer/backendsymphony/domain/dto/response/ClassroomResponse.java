package my_computer.backendsymphony.domain.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ClassroomResponse {
    String id;
    String name;
    String image;
    LocalDateTime startTime;
    Integer duration;
    LocalDateTime createdAt;
    String leaderId;
    String leaderName;
}
