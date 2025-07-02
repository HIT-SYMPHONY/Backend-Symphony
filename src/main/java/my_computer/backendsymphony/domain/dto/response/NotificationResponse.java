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
public class NotificationResponse {

    private String id;

    private String content;

    private LocalDateTime createdAt;

    private String classRoomId;

    private String classRoomName;

    private String createdBy;

    private String createdByUsername;
}
