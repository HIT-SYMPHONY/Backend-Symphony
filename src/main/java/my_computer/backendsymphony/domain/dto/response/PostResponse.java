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

    private PostStatus status;

    public PostStatus getStatus() {
        if (deadline == null)
            return PostStatus.PENDING;
        LocalDateTime now = LocalDateTime.now();
        if (deadline.isBefore(now)) {
            return PostStatus.OVERDUE;
        }
        if (deadline.toLocalDate().isEqual(now.toLocalDate())) {
            return PostStatus.DUE;
        }
        return PostStatus.PENDING;
    }
}
