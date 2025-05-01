package com.pragma.plaza_service.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@AllArgsConstructor
@Data
@Builder
public class PaginationInfo<T> {

    private final List<T> list;
    private final int currentPage;
    private final int pageSize;
    private final long totalElements;
    private final int totalPages;
    private final boolean hasNextPage;
    private final boolean hasPreviousPage;

}