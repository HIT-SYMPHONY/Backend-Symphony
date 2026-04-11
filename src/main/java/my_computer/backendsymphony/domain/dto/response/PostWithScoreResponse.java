package my_computer.backendsymphony.domain.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import my_computer.backendsymphony.constant.PostStatus;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PostWithScoreResponse {

    private String id;

    private String title;

    private LocalDateTime deadline;

    private LocalDateTime createdAt;

    private Double commentPostScore;

    private LocalDateTime commentPostUpdatedAt;

    private String createdBy;
}