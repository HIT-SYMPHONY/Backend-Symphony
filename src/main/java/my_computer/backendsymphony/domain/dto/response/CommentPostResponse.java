package my_computer.backendsymphony.domain.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import my_computer.backendsymphony.domain.entity.Post;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CommentPostResponse {

    private String id;

    private String content;

    private Double score;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private String postId;

    private String username;
}
