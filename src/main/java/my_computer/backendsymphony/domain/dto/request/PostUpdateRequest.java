package my_computer.backendsymphony.domain.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class PostUpdateRequest {
    private String title;
    private String content;
    private LocalDateTime deadline;
}