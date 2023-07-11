package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.model.*;
import ar.edu.itba.paw.service.RestaurantService;
import ar.edu.itba.paw.webapp.Utils;
import ar.edu.itba.paw.webapp.dto.RestaurantDTO;
import ar.edu.itba.paw.webapp.form.RestaurantForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Path("restaurants")
@Component
public class RestaurantController {

    private static final Logger LOGGER = LoggerFactory.getLogger(RestaurantController.class);

    @Autowired
    private RestaurantService rs;

    @Context
    private UriInfo uriInfo;

    @Autowired
    public RestaurantController(final RestaurantService rs) {
        this.rs = rs;
    }

    // GET /restaurant
    @GET
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response readRestaurants(
            @QueryParam("page") @DefaultValue("1") @Min(value = 1) final int page,
            @QueryParam("match") final String match,
            @QueryParam("category") final Category category,
            @QueryParam("zone") final Zone zone,
            @QueryParam("shift") final Shift shift
    ) {
        LOGGER.debug("GET to /restaurants with page={}, match={}, category={}, zone={}, shift={}",
                page, match, category, zone, shift);
        // TODO: Check validation of params (min for page, enums in range)

        final PagedQuery<Restaurant> restaurantPagedQuery = rs.filter(page, match, category, shift, zone);

        if (restaurantPagedQuery.getContent().isEmpty()) {
            return Response.noContent().build();
        }

        final List<RestaurantDTO> restaurantDTOList = restaurantPagedQuery.getContent().
                stream().map(r -> RestaurantDTO.fromRestaurant(uriInfo, r)).collect(Collectors.toList());

        UriBuilder uriBuilder = uriInfo.getRequestUriBuilder().replacePath("restaurants");
        Response.ResponseBuilder baseResponse = Response.ok(new GenericEntity<List<RestaurantDTO>>(restaurantDTOList){});
        return Utils.addLinksFromPagedQuery(restaurantPagedQuery, uriBuilder, baseResponse).build();
    }

    @POST
    @Consumes({MediaType.APPLICATION_JSON}) // TODO: Check Multipart form for image upload
    @PreAuthorize("@securityManager.isRestaurantOwnerWithoutRestaurant(authentication)")
    public Response createRestaurant(@Valid final RestaurantForm restaurantForm) {
        LOGGER.debug("{}", restaurantForm);
        byte[] image = null;
        if (restaurantForm.getImage() != null) {
            try { // TODO: Check if we could migrate this to form with custom mapper
                image = restaurantForm.getImage().getBytes();
            } catch (IOException e) {
                throw new IllegalStateException(); // This should never happen because of @ValidImage.
            }
        }
        Restaurant newRestaurant = rs.create(restaurantForm.getName(),
                image,
                restaurantForm.getAddress(),
                restaurantForm.getEmail(),
                restaurantForm.getDetail(),
                Zone.getByName(restaurantForm.getZone()),
                restaurantForm.getLat(),
                restaurantForm.getLng(),
                restaurantForm.getCategories(),
                restaurantForm.getShifts());
        final URI location = uriInfo.getAbsolutePathBuilder().path(String.valueOf(newRestaurant.getId())).build();
        return Response.created(location).build();
    }

    // GET /restaurants/{id}
    @GET
    @Produces(value = {MediaType.APPLICATION_JSON})
    @Path("/{id}")
    public Response getRestaurant(@PathParam("id") final long restaurantID) {
        Optional<RestaurantDTO> maybeRestaurant = rs.getById(restaurantID).map(r -> RestaurantDTO.fromRestaurant(uriInfo, r));
        if (! maybeRestaurant.isPresent()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
         return Response.ok(maybeRestaurant.get()).build();
    }

    @PUT
    @Consumes({MediaType.APPLICATION_JSON})
    @Path("/{id}")
    @PreAuthorize("@securityManager.isRestaurantOwnerOfId(authentication, #restaurantId)")
    public Response updateRestaurant(@PathParam("id") final long restaurantId, @Valid final RestaurantForm restaurantForm) {
        byte[] image = null;
        if (restaurantForm.getImage() != null) {
            try {
                image = restaurantForm.getImage().getBytes();
            } catch (IOException e) {
                throw new IllegalStateException(); // This should never happen because of @ValidImage.
            }
        }
        rs.updateCurrentRestaurant(restaurantForm.getName(),
                restaurantForm.getAddress(),
                restaurantForm.getEmail(),
                restaurantForm.getDetail(),
                Zone.getByName(restaurantForm.getZone()),
                restaurantForm.getLat(),
                restaurantForm.getLng(),
                restaurantForm.getCategories(),
                restaurantForm.getShifts(),
                image);
        return Response.ok().build();
    }

}
