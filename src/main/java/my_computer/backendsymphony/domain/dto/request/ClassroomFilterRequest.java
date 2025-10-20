package my_computer.backendsymphony.domain.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;
import my_computer.backendsymphony.constant.ClassroomStatus;
import my_computer.backendsymphony.domain.dto.pagination.PaginationFullRequestDto;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ClassroomFilterRequest extends PaginationFullRequestDto {
    ClassroomStatus status;
    Integer startYear;
}
