package my_computer.backendsymphony.domain.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;
import my_computer.backendsymphony.constant.ClassroomStatus;

import java.time.LocalDate;
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
    LocalDate startTime;
    String timeSlot;
    LocalDate endTime;
    Integer duration;
    String description;
    LocalDateTime createdAt;
    String leaderId;
    String leaderName;
    ClassroomStatus status;

    public ClassroomStatus getStatus() {
        if (startTime == null || endTime == null) {
            return ClassroomStatus.UPCOMING;
        }
        LocalDate today = LocalDate.now();
        if (startTime.isAfter(today)) return ClassroomStatus.UPCOMING;
        if (today.isAfter(endTime)) return ClassroomStatus.COMPLETED;
        return ClassroomStatus.ONGOING;
    }

}
