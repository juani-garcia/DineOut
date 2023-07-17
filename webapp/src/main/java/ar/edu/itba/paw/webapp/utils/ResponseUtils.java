package ar.edu.itba.paw.webapp.utils;

import ar.edu.itba.paw.model.PagedQuery;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

public class ResponseUtils {

    public static <T> Response.ResponseBuilder addLinksFromPagedQuery(PagedQuery<T> query, UriBuilder uri, Response.ResponseBuilder response) {
        response = response.header("X-Total-Pages", query.getPageCount());
        response = response.link(uri.clone().queryParam("page", 1).build(), "first");
        response = response.link(uri.clone().queryParam("page", query.getPageCount()).build(), "last");
        if(query.getPage() > 1) {
            response = response.link(uri.clone().queryParam("page", query.getPage() - 1).build(), "prev");
        }
        if(query.getPage() < query.getPageCount()) {
            response = response.link(uri.clone().queryParam("page", query.getPage() + 1).build(), "next");
        }
        return response;
    }

}
