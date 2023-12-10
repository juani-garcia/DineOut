package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.model.*;
import ar.edu.itba.paw.service.RestaurantService;
import ar.edu.itba.paw.webapp.utils.ResponseUtils;
import ar.edu.itba.paw.webapp.dto.RestaurantDTO;
import ar.edu.itba.paw.webapp.form.RestaurantForm;
import org.apache.commons.io.IOUtils;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;
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
import java.io.InputStream;
import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Path("restaurants")
@Component
public class RestaurantController {

    private static final Logger LOGGER = LoggerFactory.getLogger(RestaurantController.class);

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
            @QueryParam("shift") final Shift shift,
            @QueryParam("favoriteOf") final Long favoriteOf,
            @QueryParam("recommendedFor") final Long recommendedFor
    ) {
        LOGGER.debug("GET to /restaurants with page={}, match={}, category={}, zone={}, shift={}, favoriteOf={}",
                page, match, category, zone, shift, favoriteOf);

        final FilterParams params = new FilterParams()
                .setPage(page)
                .setMatch(match)
                .setCategory(category)
                .setZone(zone)
                .setShift(shift)
                .setFavoriteOf(favoriteOf)
                .setRecommendedFor(recommendedFor);

        final PagedQuery<Restaurant> restaurantPagedQuery = rs.filter(params);

        if (restaurantPagedQuery.getContent().isEmpty()) {
            return Response.noContent().build();
        }

        final List<RestaurantDTO> restaurantDTOList = restaurantPagedQuery.getContent().
                stream().map(r -> RestaurantDTO.fromRestaurant(uriInfo, r)).collect(Collectors.toList());

        UriBuilder uriBuilder = uriInfo.getRequestUriBuilder().replacePath("restaurants");
        Response.ResponseBuilder baseResponse = Response.ok(new GenericEntity<List<RestaurantDTO>>(restaurantDTOList){});
        return ResponseUtils.addLinksFromPagedQuery(restaurantPagedQuery, uriBuilder, baseResponse).build();
    }

    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    @PreAuthorize("@securityManager.isRestaurantOwnerWithoutRestaurant(authentication)")
    public Response createRestaurant(@Valid final RestaurantForm restaurantForm) {
        LOGGER.debug("{}", restaurantForm);
        // TODO: Remove this comment, only here due to TODOs
//        byte[] image = null;
//        if (restaurantForm.getImage() != null) {
//            try { // TODO: Check if we could migrate this to form with custom mapper
//                image = restaurantForm.getImage().getBytes();
//            } catch (IOException e) {
//                throw new IllegalStateException(); // This should never happen because of @ValidImage.
//                // TODO: Check https://bitbucket.org/itba/paw-2022a-10/pull-requests/122#comment-410597884
//            }
//        }
        Restaurant newRestaurant = rs.create(restaurantForm.getName(),
                null, // TODO: Remove image from restaurant creation service
                restaurantForm.getAddress(),
                restaurantForm.getEmail(),
                restaurantForm.getDetail(),
                restaurantForm.getZone(),
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
    public Response updateRestaurant(
            @PathParam("id") final long restaurantId,
            @Valid final RestaurantForm restaurantForm
    ) { // TODO: Remove image from form
        rs.updateCurrentRestaurant(restaurantForm.getName(),
                restaurantForm.getAddress(),
                restaurantForm.getEmail(),
                restaurantForm.getDetail(),
                restaurantForm.getZone(),
                restaurantForm.getLat(),
                restaurantForm.getLng(),
                restaurantForm.getCategories(),
                restaurantForm.getShifts(),
                null); // TODO: Refactor restaurant update service to not expect image
        return Response.ok().build();
    }

    @GET
    @Produces({org.springframework.http.MediaType.IMAGE_JPEG_VALUE, org.springframework.http.MediaType.IMAGE_PNG_VALUE})
    @Path("/{id}/image")
    public Response getRestaurantImage(@PathParam("id") final long restaurantID, @Context Request request) {
        Optional<Restaurant> maybeRestaurant = rs.getById(restaurantID);
        if (! maybeRestaurant.isPresent()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        Restaurant restaurant = maybeRestaurant.get();
        if (restaurant.getImage() == null) {
            LOGGER.debug("There is no image");
            return Response.noContent().build();
        }
        return ResponseUtils.addCacheToImage(request, restaurant.getImage());
    }

    @PUT
    @Consumes({MediaType.MULTIPART_FORM_DATA})
    @Path("/{id}/image")
    @PreAuthorize("@securityManager.isRestaurantOwnerOfId(authentication, #restaurantId)")
    public Response updateRestaurantImage(
            @PathParam("id") final long restaurantId,
            @FormDataParam("image") InputStream fileInputStream,
            @FormDataParam("image") FormDataContentDisposition fileMetaData
    ) throws IOException {
        LOGGER.debug("Loading image for restaurant {}", restaurantId);
        final byte[] image = IOUtils.toByteArray(fileInputStream);
        rs.updateRestaurantImage(restaurantId, image);
        return Response.ok().build();
    }

    @DELETE
    @Path("/{id}/image")
    @PreAuthorize("@securityManager.isRestaurantOwnerOfId(authentication, #restaurantId)")
    public Response deleteRestaurantImage(
            @PathParam("id") final long restaurantId
    ) {
        rs.updateRestaurantImage(restaurantId, null);
        return Response.ok().build();
    }

}
