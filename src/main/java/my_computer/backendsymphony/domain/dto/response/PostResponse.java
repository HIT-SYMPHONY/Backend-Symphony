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
public class PostResponse {

     private String id;

    private String title;

    private String content;

    private LocalDateTime deadline;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private String createdBy;

    private String classRoomId;

    private String classRoomName;

    private String creatorName;
}
