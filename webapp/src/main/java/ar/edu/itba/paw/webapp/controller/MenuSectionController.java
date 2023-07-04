package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.model.MenuSection;
import ar.edu.itba.paw.service.MenuSectionService;
import ar.edu.itba.paw.webapp.dto.MenuSectionDTO;
import ar.edu.itba.paw.webapp.dto.RestaurantDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.constraints.Min;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
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

}
