package my_computer.backendsymphony.domain.dto.request;

import lombok.Data;

import java.time.DayOfWeek;
import java.time.LocalTime;

@Data
public class LessonUpdateRequest {
    private String content;

    private String title;

    private String location;

    private LocalTime startTime;

    private LocalTime endTime;

    private DayOfWeek dayOfWeek;

    private String leaderName;
}
