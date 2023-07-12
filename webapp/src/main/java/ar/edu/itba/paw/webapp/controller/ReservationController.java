package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.model.PagedQuery;
import ar.edu.itba.paw.model.Reservation;
import ar.edu.itba.paw.model.Restaurant;
import ar.edu.itba.paw.model.Zone;
import ar.edu.itba.paw.service.ReservationService;
import ar.edu.itba.paw.service.RestaurantService;
import ar.edu.itba.paw.service.SecurityService;
import ar.edu.itba.paw.webapp.Utils;
import ar.edu.itba.paw.webapp.dto.ReservationDTO;
import ar.edu.itba.paw.webapp.dto.RestaurantDTO;
import ar.edu.itba.paw.webapp.form.ReservationConfirmationForm;
import ar.edu.itba.paw.webapp.form.ReservationForm;
import ar.edu.itba.paw.webapp.form.RestaurantForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.method.P;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PatchMapping;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Path("reservations")
@Component
public class ReservationController {

    private final ReservationService rs;
    private final SecurityService ss;

    @Context
    private UriInfo uriInfo;

    @Autowired
    public ReservationController(final ReservationService rs, final SecurityService ss) {
        this.rs = rs;
        this.ss = ss;
    }

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @PreAuthorize("@securityManager.isUserOfId(authentication, #userId) or @securityManager.isUserOfId(authentication, #restaurantId)")
    // TODO: Check what happens when parameters are null (auth function receives long, controller receives Long)
    public Response readReservations(
            @QueryParam("page") @DefaultValue("1") @Min(value = 1) final int page,
            @QueryParam("byUser") final Long userId,
            @QueryParam("forRestaurant") final Long restaurantId,
            @QueryParam("past") @DefaultValue("false") final boolean past
    ) {
        if ((userId != null && restaurantId != null) || (userId == null && restaurantId == null)) {
            return Response.status(Response.Status.BAD_REQUEST).build(); // TODO: Check if we should send message
        }
        // TODO: Check if we'll change this
        final PagedQuery<Reservation> reservationPagedQuery = (userId != null) ? rs.getAllForCurrentUser(page, past) : rs.getAllForCurrentRestaurant(page, past);
        if (reservationPagedQuery.getContent().isEmpty()) {
            return Response.noContent().build();
        }
        final List<ReservationDTO> reservationDTOList = reservationPagedQuery.getContent()
                .stream().map(r -> ReservationDTO.fromReservation(uriInfo, r))
                .collect(Collectors.toList());
        Response.ResponseBuilder responseBuilder = Response.ok(new GenericEntity<List<ReservationDTO>>(reservationDTOList){});
        return Utils.addLinksFromPagedQuery(
                reservationPagedQuery,
                uriInfo.getRequestUriBuilder(),
                responseBuilder).build();
    }

    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    @Secured("ROLE_DINER")
    public Response createReservation(
            @Valid final ReservationForm reservationForm
    ) {
        Reservation reservation = rs.create(
                reservationForm.getRestaurantId(),
                ss.getCurrentUsername(),
                reservationForm.getAmount(),
                reservationForm.getLocalDateTime(),
                reservationForm.getComments(),
                uriInfo.getBaseUri().getPath() // TODO: Check if this replaces: uriInfo.getRequestURL().toString().replace(request.getServletPath(), "")
        );
        final URI location = uriInfo.getAbsolutePathBuilder().path(String.valueOf(reservation.getId())).build();
        return Response.created(location).build();
    }


    @PatchMapping
    @Path("/{id}")
    @PreAuthorize("@securityManager.isReservationRestaurant(authentication, #reservationId)")
    public Response updateRestaurantConfirmation(
            @PathParam("id") final long reservationId,
            @Valid ReservationConfirmationForm reservationConfirmationForm
    ) {
        // TODO: Should we support unconfirmation?
        rs.confirm(reservationId, uriInfo.getBaseUri().getPath());
        return Response.ok().build();
    }

    @DELETE
    @Path("/{id}")
    // TODO: Check if authorization levels are alright
    @PreAuthorize("@securityManager.isReservationOwner(authentication, #reservationId) or @securityManager.isReservationRestaurant(authentication, #reservationId)")
    public Response deleteReservation(
            @PathParam("id") final long reservationId
    ) {
        // TODO: Check contextPath
        rs.delete(reservationId, uriInfo.getBaseUri().getPath());
        return Response.noContent().build();
    }

}