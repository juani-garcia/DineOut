package ar.edu.itba.paw.model;

import java.util.Collections;
import java.util.List;

public class PagedQuery<T> {

    private final List<T> content;
    private final Long page, pageCount;

    public PagedQuery(List<T> content, Long page, Long pageCount) {
        this.content = content;
        this.page = page;
        this.pageCount = pageCount;
    }

    public List<T> getContent() {
        return content;
    }

    public Long getPage() {
        return page;
    }

    public Long getPageCount() {
        return pageCount;
    }

    public static <T> PagedQuery<T> emptyPage() {
        return new PagedQuery<T>(Collections.emptyList(), 1L, 1L);
    }
}
