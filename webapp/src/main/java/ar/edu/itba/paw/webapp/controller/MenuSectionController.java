package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.model.MenuSection;
import ar.edu.itba.paw.model.exceptions.MenuSectionNotFoundException;
import ar.edu.itba.paw.service.MenuSectionService;
import ar.edu.itba.paw.webapp.dto.MenuItemDTO;
import ar.edu.itba.paw.webapp.dto.MenuSectionDTO;
import ar.edu.itba.paw.webapp.form.MenuSectionForm;
import ar.edu.itba.paw.webapp.form.MenuSectionUpdateForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Path("restaurants/{restaurantId}/menu-sections")
@Component
public class MenuSectionController {

    @Autowired
    private MenuSectionService mss;

    @Context
    private UriInfo uriInfo;

    private static final Logger LOGGER = LoggerFactory.getLogger(MenuSectionController.class);

    @PathParam("restaurantId")
    private long restaurantId;

    @GET
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response readMenuSections() {
        LOGGER.debug("Retrieving menu sections for restaurant with id: {}", restaurantId);

        List<MenuSection> menuSectionList = mss.getByRestaurantId(restaurantId); // TODO: Discuss if this should be a PagedQuery
        if (menuSectionList.isEmpty()) {
            return Response.noContent().build();
        }

        final List<MenuSectionDTO> menuSectionDTOList = menuSectionList.stream()
                .map(ms -> MenuSectionDTO.fromMenuSection(uriInfo, ms)).collect(Collectors.toList());

        return Response.ok(new GenericEntity<List<MenuSectionDTO>>(menuSectionDTOList){}).build();
    }

    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    @PreAuthorize("@securityManager.isRestaurantOwnerOfId(authentication, #restaurantId)")
    public Response createMenuSection(
            @PathParam("restaurantId") final long restaurantId,
            @Valid final MenuSectionForm menuSectionForm
    ) {
        final MenuSection menuSection = mss.create(menuSectionForm.getName());
        final URI location = uriInfo.getAbsolutePathBuilder().path(String.valueOf(menuSection.getId())).build();
        return Response.created(location).build();
    }

    @GET
    @Path("/{menuSectionId}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response readMenuSection(
            @PathParam("menuSectionId") final long menuSectionId
    ) {
        Optional<MenuSectionDTO> maybeMenuSection = mss.getById(menuSectionId).map(ms -> MenuSectionDTO.fromMenuSection(uriInfo, ms));
        if (! maybeMenuSection.isPresent()) {
            throw new MenuSectionNotFoundException();
        }
        return Response.ok(maybeMenuSection.get()).build();
    }

    @PUT
    @Path("/{menuSectionId}")
    @Consumes({MediaType.APPLICATION_JSON})
    @PreAuthorize("@securityManager.isMenuSectionOwnerOfId(authentication, #menuSectionId)")
    public Response updateMenuSection(
            @PathParam("restaurantId") final long restaurantId,
            @PathParam("menuSectionId") final long menuSectionId,
            @Valid final MenuSectionUpdateForm menuSectionForm
    ) {
        mss.update(menuSectionId,
                menuSectionForm.getName(),
                menuSectionForm.getMenuItemsOrder().stream().map(MenuItemDTO::getIdFromURI).collect(Collectors.toList()));
        return Response.ok().build();
    }

    @DELETE
    @Path("/{menuSectionId}")
    @PreAuthorize("@securityManager.isMenuSectionOwnerOfId(authentication, #menuSectionId)")
    public Response deleteMenuSection(
            @PathParam("restaurantId") final long restaurantId,
            @PathParam("menuSectionId") final long menuSectionId
    ) {
        mss.delete(menuSectionId);
        return Response.noContent().build();
    }

}
