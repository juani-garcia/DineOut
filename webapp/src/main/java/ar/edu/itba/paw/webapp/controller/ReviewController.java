package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.model.*;
import ar.edu.itba.paw.service.RestaurantReviewService;
import ar.edu.itba.paw.webapp.utils.ResponseUtils;
import ar.edu.itba.paw.webapp.dto.RestaurantReviewDTO;
import ar.edu.itba.paw.webapp.form.RestaurantReviewForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Path("reviews")
@Component
public class ReviewController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReviewController.class);

    private RestaurantReviewService rrs;

    @Context
    private UriInfo uriInfo;

    @Autowired
    public ReviewController(final RestaurantReviewService restaurantReviewService) {
        this.rrs = restaurantReviewService;
    }

    @GET
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response readReviews(
            @QueryParam("page") @DefaultValue("1") @Min(value = 1) final int page,
            @QueryParam("pageSize") @DefaultValue("5") @Min(value = 1) final int pageSize,
            @QueryParam("byUser") final Long userId,
            @QueryParam("forRestaurant") final Long restaurantId
    ) {
        final PagedQuery<RestaurantReview> restaurantReviewPagedQuery = rrs.get(page, pageSize, restaurantId, userId);
        if (restaurantReviewPagedQuery.getContent().isEmpty()) {
            return Response.noContent().build();
        }
        final List<RestaurantReviewDTO> restaurantReviewDTOList = restaurantReviewPagedQuery.getContent()
                .stream().map(rr -> RestaurantReviewDTO.fromRestaurantReview(uriInfo, rr))
                .collect(Collectors.toList());

        UriBuilder uriBuilder = uriInfo.getRequestUriBuilder();
        Response.ResponseBuilder responseBuilder = Response.ok(new GenericEntity<List<RestaurantReviewDTO>>(restaurantReviewDTOList){});
        return ResponseUtils.addLinksFromPagedQuery(restaurantReviewPagedQuery, uriBuilder, responseBuilder).build();
    }

    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    @Secured("ROLE_DINER")
    public Response createReview(
            @Valid final RestaurantReviewForm restaurantReviewForm
    ) {
        final RestaurantReview restaurantReview = rrs.create(
                restaurantReviewForm.getReview(),
                restaurantReviewForm.getRating(),
                restaurantReviewForm.getRestaurantId(),
                uriInfo.getBaseUri().getPath()
        );
        final URI location = uriInfo.getAbsolutePathBuilder().path(String.valueOf(restaurantReview.getId())).build();
        return Response.created(location).build();
    }

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @Path("/{id}")
    public Response readReview(
            @PathParam("id") final long reviewId
    ) {
        Optional<RestaurantReviewDTO> reviewDTOOptional = rrs.getById(reviewId)
                .map(r -> RestaurantReviewDTO.fromRestaurantReview(uriInfo, r));
        if (! reviewDTOOptional.isPresent()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(reviewDTOOptional.get()).build();
    }

    @PUT
    @Consumes({MediaType.APPLICATION_JSON})
    @Path("/{id}")
    @PreAuthorize("@securityManager.isReviewOwner(authentication, #reviewId)")
    public Response updateReview(
            @PathParam("id") final long reviewId,
            @Valid final RestaurantReviewForm restaurantReviewForm
    ) {
        rrs.edit(
                reviewId,
                restaurantReviewForm.getReview(),
                restaurantReviewForm.getRating()
        );
        return Response.ok().build();
    }

    @DELETE
    @Path("/{id}")
    @PreAuthorize("@securityManager.isReviewOwner(authentication, #reviewId)")
    public Response deleteReview(
            @PathParam("id") final long reviewId
    ) {
        rrs.delete(reviewId);
        return Response.noContent().build();
    }

}
