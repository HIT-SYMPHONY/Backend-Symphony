package my_computer.backendsymphony.domain.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import my_computer.backendsymphony.constant.CompetitionStatus;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CompetitionSummaryResponse {
    private String id;
    private String name;
    private String image;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Boolean isRegistered;
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
