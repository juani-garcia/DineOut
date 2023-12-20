package ar.edu.itba.paw.webapp.utils;

import ar.edu.itba.paw.model.Image;
import ar.edu.itba.paw.model.PagedQuery;

import javax.ws.rs.core.*;
import java.util.Arrays;

public class ResponseUtils {

    public static <T> Response.ResponseBuilder addLinksFromPagedQuery(PagedQuery<T> query, UriBuilder uri, Response.ResponseBuilder response) {
        response = response.header("DineOut-Total-Pages", query.getPageCount());
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

    public static Response addCacheControl(Request request, EntityTag tag, Object entity) {
        Response.ResponseBuilder response = request.evaluatePreconditions(tag);
        if(response == null) {
            response = Response.ok(entity).tag(tag);
        }
        final CacheControl cacheControl = new CacheControl();
        cacheControl.setNoTransform(true);
        cacheControl.setMustRevalidate(true);

        return response.cacheControl(cacheControl).build();
    }

    public static Response addCacheToImage(Request request, Image image) {
        EntityTag tag = new EntityTag(String.valueOf(Arrays.hashCode(image.getSource())));
        return addCacheControl(request, tag, image.getSource());
    }

}
