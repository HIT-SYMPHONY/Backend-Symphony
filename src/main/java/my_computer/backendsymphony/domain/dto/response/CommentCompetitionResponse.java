package my_computer.backendsymphony.domain.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import my_computer.backendsymphony.domain.entity.Competition;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommentCompetitionResponse {

    private String id;

    private Double score;

    private String content;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private String createdByUsername;

    private String competitionId;
}
