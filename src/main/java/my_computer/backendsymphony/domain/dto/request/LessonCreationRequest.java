package my_computer.backendsymphony.domain.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class LessonCreationRequest {
    @NotBlank(message = "Nội dung không được trống")
    private String content;

    @NotBlank(message = "Địa điểm không được để trống")
    private String location;

    @NotBlank(message = "Khung giờ không được trống")
    private String timeSlot;

    @NotNull(message = "ID lớp học không được trống")
    private String classRoomId;

    @NotBlank(message = "Tên leader không được để trống")
    private String leaderName;

    @NotBlank(message = "Tên lớp học không được để trống")
    private String className;
}
