package my_computer.backendsymphony.domain.dto.pagination;

import lombok.Getter;
import lombok.Setter;
import my_computer.backendsymphony.constant.CommonConstant;

@Getter
@Setter
public class PaginationSortRequestDto extends PaginationRequestDto {
    private String sortBy = CommonConstant.EMPTY_STRING;

    private Boolean isAscending = Boolean.FALSE;

}
