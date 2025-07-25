package my_computer.backendsymphony.domain.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CommentCompetitionResponse {

    private String id;

    private Double score;

    private String content;

   private LocalDateTime createdAt;

   private LocalDateTime updatedAt;

   private String createdByUserName;

    private String competitionName;
}
