package com.swoqe.newsstand.utils;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@Data
public class PageLink {

    private final String textSearch;
    private final int pageSize;
    private final int page;
    private final SortOrder sortOrder;

    public PageLink(PageLink pageLink) {
        this.pageSize = pageLink.getPageSize();
        this.page = pageLink.getPage();
        this.textSearch = pageLink.getTextSearch();
        this.sortOrder = pageLink.getSortOrder();
    }

    public PageLink(int pageSize) {
        this(pageSize, 0);
    }

    public PageLink(int pageSize, int page) {
        this(pageSize, page, null, null);
    }

    public PageLink(int pageSize, int page, String textSearch) {
        this(pageSize, page, textSearch, null);
    }

    public PageLink(int pageSize, int page, String textSearch, SortOrder sortOrder) {
        this.pageSize = pageSize;
        this.page = page;
        this.textSearch = textSearch;
        this.sortOrder = sortOrder;
    }

    @JsonIgnore
    public PageLink nextPageLink() {
        return new PageLink(this.pageSize, this.page+1, this.textSearch, this.sortOrder);
    }

    public Pageable toPageable() {
        return PageRequest.of(page, pageSize, toSort(sortOrder));
    }

    public Sort toSort(SortOrder sortOrder) {
        if (sortOrder == null) {
            return Sort.unsorted();
        } else {
            String property = sortOrder.getProperty();
            return Sort.by(Sort.Direction.fromString(sortOrder.getDirection().name()), property);
        }
    }

    public static PageLink createPageLink(int pageSize, int page, String textSearch, String sortProperty, String sortOrder) throws IllegalArgumentException {
        if (!StringUtils.isEmpty(sortProperty)) {
            SortOrder.Direction direction = SortOrder.Direction.ASC;
            if (!StringUtils.isEmpty(sortOrder)) {
                try {
                    direction = SortOrder.Direction.valueOf(sortOrder.toUpperCase());
                } catch (IllegalArgumentException e) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Unsupported sort order '" + sortOrder + "'! Only 'ASC' or 'DESC' types are allowed.");
                }
            }
            SortOrder sort = new SortOrder(sortProperty, direction);
            return new PageLink(pageSize, page, textSearch, sort);
        } else {
            return new PageLink(pageSize, page, textSearch);
        }
    }
}