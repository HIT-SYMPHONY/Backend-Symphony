package my_computer.backendsymphony.domain.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CommentPostResponse {
    private String id;
    private String content;
    private Double score;
    private String feedback;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String postId;
    private String createdBy;
    private String fullName;
    private String studentCode;
}
