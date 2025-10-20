package my_computer.backendsymphony.domain.dto.request;

import lombok.Getter;
import lombok.Setter;
import my_computer.backendsymphony.constant.Role;
import my_computer.backendsymphony.domain.dto.pagination.PaginationFullRequestDto;

@Getter
@Setter
public class UserFilterRequest extends PaginationFullRequestDto {
    private Role role;
    private String intake;
}
