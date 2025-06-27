package my_computer.backendsymphony.domain.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LessonResponse {
    private String content;
    private String location;
    private String timeSlot;
    private String leaderName;
    private String className;
}
