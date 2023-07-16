package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.model.MenuItem;
import ar.edu.itba.paw.service.MenuItemService;
import ar.edu.itba.paw.webapp.dto.MenuItemDTO;
import ar.edu.itba.paw.webapp.form.MenuItemForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
    @Produces({MediaType.APPLICATION_JSON})
    public Response readMenuItems() {
        List<MenuItem> menuItemList = mis.getBySectionId(menuSectionId);
        if (menuItemList.isEmpty()) {
            return Response.noContent().build();
        }

        final List<MenuItemDTO> menuItemDTOList = menuItemList.stream()
                .map(mi -> MenuItemDTO.fromMenuItem(uriInfo, mi))
                .collect(Collectors.toList());
        return Response.ok(new GenericEntity<List<MenuItemDTO>>(menuItemDTOList){}).build();
    }

    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    @PreAuthorize("@securityManager.isRestaurantOwnerOfId(authentication, #restaurantId)")
    public Response createMenuItem(
            @Valid final MenuItemForm menuItemForm
    ) {
        final MenuItem menuItem = mis.create(
                menuItemForm.getName(),
                menuItemForm.getDetail(),
                menuItemForm.getPrice(),
                menuSectionId,
                null
        );
        final URI location = uriInfo.getAbsolutePathBuilder().path(String.valueOf(menuItem.getId())).build();
        return Response.created(location).build();
    }

    @GET
    @Path("/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response readMenuItem(
            @PathParam("id") final long menuItemId
    ) {
        Optional<MenuItemDTO> maybeMenuItem = mis.getById(menuItemId).map(mi -> MenuItemDTO.fromMenuItem(uriInfo, mi));
        if (!maybeMenuItem.isPresent()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(maybeMenuItem.get()).build();
    }

    @PUT
    @Path("/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    @PreAuthorize("@securityManager.isRestaurantOwnerOfId(authentication, #restaurantId)")
    public Response updateMenuItem(
            @PathParam("id") final long menuItemId,
            @Valid final MenuItemForm menuItemForm
    ) {
        mis.edit(
                menuItemId,
                menuItemForm.getName(),
                menuItemForm.getDetail(),
                menuItemForm.getPrice(),
                menuItemForm.getMenuSectionId(),
                null // TODO: Change update so as to not erase image
        );
        return Response.ok().build();
    }

    @DELETE
    @Path("/{id}")
    @PreAuthorize("@securityManager.isRestaurantOwnerOfId(authentication, #restaurantId)")
    public Response deleteMenuItem(
            @PathParam("id") final long menuItemId
    ) {
        mis.delete(menuItemId);
        return Response.noContent().build();
    }
}
