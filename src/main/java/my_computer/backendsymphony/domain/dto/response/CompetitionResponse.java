package my_computer.backendsymphony.domain.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import my_computer.backendsymphony.constant.CompetitionStatus;

import java.time.LocalDateTime;

@Getter
@Builder
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CompetitionResponse {
    String id;
    String name;
    String description;
    String content;
    String image;
    LocalDateTime startTime;
    LocalDateTime endTime;
    CompetitionStatus status;

    public CompetitionStatus getStatus() {
        if (startTime == null || endTime == null) return null;
        LocalDateTime now = LocalDateTime.now();
        if (now.isBefore(startTime)) return CompetitionStatus.UPCOMING;
        if (now.isAfter(endTime)) return CompetitionStatus.COMPLETED;
        if (now.isAfter(startTime) && now.isBefore(endTime)) {
            return CompetitionStatus.ONGOING;
        }
        return null;
    }
}
