package my_computer.backendsymphony.domain.dto.response;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;
import my_computer.backendsymphony.constant.ErrorMessage;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
public class LessonResponse {
    private String id;
    private String title;
    private String content;
    private String location;
    private LocalTime startTime;
    private LocalTime endTime;
    private DayOfWeek dayOfWeek;
    private LocalDateTime createdAt;
    private String leaderName;
    private String className;
}
