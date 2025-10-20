package my_computer.backendsymphony.domain.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;
import my_computer.backendsymphony.domain.dto.pagination.PaginationFullRequestDto;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LessonFilterRequest extends PaginationFullRequestDto {
    String status;
}
