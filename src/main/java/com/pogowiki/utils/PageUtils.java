package com.pogowiki.utils;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import javax.persistence.Query;
import java.util.List;
import java.util.Objects;

public class PageUtils<T> {

    public static Pageable getPageable(Integer page, Integer size) {
        if (page == null && size == null) {
            return Pageable.unpaged();
        }
        if (page == null || page < 0) {
            page = 0;
        }
        if (size == null || size < 5) {
            size = 5;
        }
        return PageRequest.of(page, size);
    }

    public static void setPageable(Query query, Pageable pageable) {
        if (pageable != null) {
            query.setFirstResult(pageable.getPageNumber() * pageable.getPageSize());
            query.setMaxResults(pageable.getPageSize());
        }
    }

    public Page<T> setPage(List<T> data, Pageable pageable, long totalNumber) {
        return new PageImpl<>(data, Objects.requireNonNullElseGet(pageable, Pageable::unpaged), totalNumber);
    }
}
