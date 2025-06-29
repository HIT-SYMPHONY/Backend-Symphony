package my_computer.backendsymphony.domain.dto.pagination;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import my_computer.backendsymphony.constant.CommonConstant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PaginationRequestDto {

    private Integer pageNum = 1;

    private Integer pageSize = 10;

    public int getPageNum() {
        if (this.pageNum == null || this.pageNum < 1) {
            return 0;
        }
        return this.pageNum - 1;
    }

    public int getPageSize() {
        if (this.pageSize == null || this.pageSize < 1) {
            return CommonConstant.PAGE_SIZE_DEFAULT;
        }
        return this.pageSize;
    }
}
