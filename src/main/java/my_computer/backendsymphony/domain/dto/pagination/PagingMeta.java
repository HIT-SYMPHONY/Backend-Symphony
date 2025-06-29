package my_computer.backendsymphony.domain.dto.pagination;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PagingMeta {
    private Long totalElements;
    private Integer totalPages;
    private Integer pageNum;
    private Integer pageSize;
    private String sortBy;
    private String sortType;
}