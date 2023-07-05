package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.service.MenuItemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

@Path("restaurants/{rId}/menu-sections/{msId}/menu-items")
@Component
public class MenuItemController {

    @Autowired
    private MenuItemService mis;

    @Context
    private UriInfo uriInfo;

    private static final Logger LOGGER = LoggerFactory.getLogger(MenuItemController.class);

    @PathParam("rId")
    private long restaurantId;

    @PathParam("msId")
    private long menuSectionId;

    @GET
    public Response get() {
        return null;
    }
}
