package my_computer.backendsymphony.util;

import my_computer.backendsymphony.constant.CommonConstant;
import my_computer.backendsymphony.constant.SortByDataConstant;
import my_computer.backendsymphony.domain.dto.pagination.PaginationRequestDto;
import my_computer.backendsymphony.domain.dto.pagination.PaginationSortRequestDto;
import my_computer.backendsymphony.domain.dto.pagination.PagingMeta;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class PaginationUtil {
    public static Pageable buildPageable(PaginationRequestDto request) {
        return PageRequest.of(request.getPageNum(), request.getPageSize());
    }

    public static Pageable buildPageable(PaginationSortRequestDto request, SortByDataConstant constant) {
        String sortByField = constant.getSortBy(request.getSortBy());
        Sort sort = Sort.by(sortByField);
        if (request.getIsAscending() != null && request.getIsAscending()) {
            sort = sort.ascending();
        } else {
            sort = sort.descending();
        }
        return PageRequest.of(request.getPageNum(), request.getPageSize(), sort);
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

    public static <T> PagingMeta buildPagingMeta(PaginationSortRequestDto request, SortByDataConstant constant, Page<T> pages) {
        return new PagingMeta(
                pages.getTotalElements(),
                pages.getTotalPages(),
                request.getPageNum() + 1,
                request.getPageSize(),
                constant.getSortBy(request.getSortBy()),
                request.getIsAscending().equals(Boolean.TRUE) ? CommonConstant.SORT_TYPE_ASC : CommonConstant.SORT_TYPE_DESC
        );
    }
}
