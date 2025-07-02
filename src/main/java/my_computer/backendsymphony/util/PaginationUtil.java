package my_computer.backendsymphony.util;

import my_computer.backendsymphony.domain.dto.pagination.PaginationRequestDto;
import my_computer.backendsymphony.domain.dto.pagination.PagingMeta;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

public class PaginationUtil {
    public static Pageable buildPageable(PaginationRequestDto request) {
        return PageRequest.of(request.getPageNum(), request.getPageSize());
    }

    public static <T> PagingMeta buildPagingMeta(PaginationRequestDto request, Page<T> page) {
        return new PagingMeta(
                page.getTotalElements(),
                page.getTotalPages(),
                request.getPageNum() + 1,
                request.getPageSize(),
                null,
                null
        );
    }
}
