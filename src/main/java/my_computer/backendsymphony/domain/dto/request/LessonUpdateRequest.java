package my_computer.backendsymphony.domain.dto.request;

import lombok.Data;

@Data
public class LessonUpdateRequest {
    private String content;

    private String location;

    private String timeSlot;

    private String leaderName;
}
