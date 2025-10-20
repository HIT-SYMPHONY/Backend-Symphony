package my_computer.backendsymphony.domain.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;
import my_computer.backendsymphony.constant.CompetitionStatus;
import my_computer.backendsymphony.domain.dto.pagination.PaginationFullRequestDto;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CompetitionFilterRequest extends PaginationFullRequestDto {
    CompetitionStatus status;
    Integer startYear;
}
