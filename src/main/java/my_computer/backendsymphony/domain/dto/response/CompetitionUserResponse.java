package my_computer.backendsymphony.domain.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import my_computer.backendsymphony.constant.CompetitionUserStatus;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CompetitionUserResponse {

    private String competitionId;
    private String userId;

    private CompetitionUserStatus status;
    private LocalDateTime submittedAt;
    private LocalDateTime joinedAt;

}
