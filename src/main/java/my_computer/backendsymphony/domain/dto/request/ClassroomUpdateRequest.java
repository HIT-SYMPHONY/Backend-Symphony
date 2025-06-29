package my_computer.backendsymphony.domain.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ClassroomUpdateRequest {
    String name;
    LocalDate startTime;
    Integer duration;
    String leaderId;
}
