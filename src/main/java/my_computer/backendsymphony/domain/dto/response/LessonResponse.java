package my_computer.backendsymphony.domain.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
public class LessonResponse {
    private String id;
    private String content;
    private String location;
    private String timeSlot;
    private LocalDateTime createdAt;
    private String leaderName;
    private String className;
}
