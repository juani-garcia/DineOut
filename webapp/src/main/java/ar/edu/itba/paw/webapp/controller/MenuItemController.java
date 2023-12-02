package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.model.MenuItem;
import ar.edu.itba.paw.service.MenuItemService;
import ar.edu.itba.paw.webapp.dto.MenuItemDTO;
import ar.edu.itba.paw.webapp.form.MenuItemForm;
import ar.edu.itba.paw.webapp.utils.PATCH;
import ar.edu.itba.paw.webapp.utils.ResponseUtils;
import org.apache.commons.io.IOUtils;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.*;
import java.util.stream.Collectors;

@Path("restaurants/{restaurantId}/menu-sections/{msId}/menu-items")
@Component
public class MenuItemController {

    @Autowired
    private MenuItemService mis;

    @Context
    private UriInfo uriInfo;

    private static final Logger LOGGER = LoggerFactory.getLogger(MenuItemController.class);

    @PathParam("restaurantId")
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
            @PathParam("restaurantId") final long restaurantId,
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
            @PathParam("restaurantId") final long restaurantId,
            @PathParam("id") final long menuItemId,
            @Valid final MenuItemForm menuItemForm
    ) {
        mis.edit(
                menuItemId,
                menuItemForm.getName(),
                menuItemForm.getDetail(),
                menuItemForm.getPrice(),
                menuItemForm.getMenuSectionId()
        );
        return Response.ok().build();
    }

    @PATCH
    @Path("/{id}")
    @PreAuthorize("@securityManager.isRestaurantOwnerOfId(authentication, #restaurantId)")
    public Response updateMenuSection(
            @PathParam("restaurantId") final long restaurantId,
            @PathParam("id") final long menuSectionId,
            @QueryParam("up") @DefaultValue("true") final boolean up
    ) {
        if (up) {
            mis.moveUp(menuSectionId);
        } else {
            mis.moveDown(menuSectionId);
        }
        return Response.ok().build();
    }

    @DELETE
    @Path("/{id}")
    @PreAuthorize("@securityManager.isRestaurantOwnerOfId(authentication, #restaurantId)")
    public Response deleteMenuItem(
            @PathParam("restaurantId") final long restaurantId,
            @PathParam("id") final long menuItemId
    ) {
        mis.delete(menuItemId);
        return Response.noContent().build();
    }

    @GET
    @Produces({org.springframework.http.MediaType.IMAGE_JPEG_VALUE, org.springframework.http.MediaType.IMAGE_PNG_VALUE})
    @Path("/{id}/image")
    public Response getMenuItemImage(@PathParam("id") final long menuItemId, @Context Request request) {
        LOGGER.debug("Getting image for menu item with id {}", menuItemId);
        Optional<MenuItem> maybeMenuItem = mis.getById(menuItemId);
        if (! maybeMenuItem.isPresent()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        MenuItem menuItem = maybeMenuItem.get();
        if (menuItem.getImage() == null) {
            LOGGER.debug("There is no image");
            return Response.noContent().build();
        }
        return ResponseUtils.addCacheToImage(request, menuItem.getImage());
    }

    @PUT
    @Consumes({MediaType.MULTIPART_FORM_DATA})
    @Path("/{id}/image")
    @PreAuthorize("@securityManager.isRestaurantOwnerOfId(authentication, #restaurantId)")
    public Response updateMenuItemImage(
            @PathParam("restaurantId") final long restaurantId,
            @PathParam("id") final long menuItemId,
            @FormDataParam("image") InputStream fileInputStream,
            @FormDataParam("image") FormDataContentDisposition fileMetaData
    ) throws IOException {
        LOGGER.debug("Uploading image for menu item {}", menuItemId);
        final byte[] image = IOUtils.toByteArray(fileInputStream); // TODO: Catch exception and throw custom one
        mis.updateImage(menuItemId, image);
        return Response.ok().build();
    }

    @DELETE
    @Path("/{id}/image")
    @PreAuthorize("@securityManager.isRestaurantOwnerOfId(authentication, #restaurantId)")
    public Response deleteMenuItemImage(
            @PathParam("restaurantId") final long restaurantId,
            @PathParam("id") final long menuItemId
    ) {
        mis.updateImage(menuItemId, null);
        return Response.ok().build();
    }

}
