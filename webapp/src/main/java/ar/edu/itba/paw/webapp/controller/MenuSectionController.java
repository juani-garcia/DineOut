package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.model.MenuSection;
import ar.edu.itba.paw.service.MenuSectionService;
import ar.edu.itba.paw.webapp.dto.MenuSectionDTO;
import ar.edu.itba.paw.webapp.dto.RestaurantDTO;
import ar.edu.itba.paw.webapp.form.MenuSectionForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.method.P;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

import javax.print.attribute.standard.Media;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.awt.*;
import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Path("restaurants/{id}/menu-sections")
@Component
public class MenuSectionController {

    @Autowired
    private MenuSectionService mss;

    @Context
    private UriInfo uriInfo;

    private static final Logger LOGGER = LoggerFactory.getLogger(MenuSectionController.class);

    @PathParam("id")
    private long restaurantId;

    @GET
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response readMenuSections() {
        LOGGER.debug("Retrieving menu sections for restaurant with id: {}", restaurantId);

        List<MenuSection> menuSectionList = mss.getByRestaurantId(restaurantId);
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
            @Valid final MenuSectionForm menuSectionForm
    ) {
        final MenuSection menuSection = mss.create(menuSectionForm.getName());
        final URI location = uriInfo.getAbsolutePathBuilder().path(String.valueOf(menuSection.getId())).build();
        return Response.created(location).build();
    }

    @GET
    @Path("/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response readMenuSection(
            @PathParam("id") final long menuSectionId
    ) {
        Optional<MenuSectionDTO> maybeMenuSection = mss.getById(menuSectionId).map(ms -> MenuSectionDTO.fromMenuSection(uriInfo, ms));
        if (! maybeMenuSection.isPresent()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(maybeMenuSection.get()).build();
    }

    @PUT
    @Path("/{id}")
    @Consumes({MediaType.APPLICATION_JSON})
    @PreAuthorize("@securityManager.isRestaurantOwnerOfId(authentication, #restaurantId)")
    public Response updateMenuSection(
            @PathParam("id") final long menuSectionId,
            @Valid final MenuSectionForm menuSectionForm
    ) {
        mss.updateName(menuSectionId, menuSectionForm.getName());
        return Response.ok().build();
    }

    @DELETE
    @Path("/{id}")
    @PreAuthorize("@securityManager.isRestaurantOwnerOfId(authentication, #restaurantId)")
    public Response deleteMenuSection(
            @PathParam("id") final long menuSectionId
    ) {
        mss.delete(menuSectionId);
        return Response.noContent().build();
    }

}
