package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.model.PagedQuery;
import ar.edu.itba.paw.model.Reservation;
import ar.edu.itba.paw.service.ReservationService;
import ar.edu.itba.paw.service.SecurityService;
import ar.edu.itba.paw.service.UserService;
import ar.edu.itba.paw.webapp.dto.ReservationDTO;
import ar.edu.itba.paw.webapp.form.ReservationConfirmationForm;
import ar.edu.itba.paw.webapp.form.ReservationForm;
import ar.edu.itba.paw.webapp.utils.PATCH;
import ar.edu.itba.paw.webapp.utils.ResponseUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@Path("reservations")
@Component
public class ReservationController {

    private final ReservationService rs;
    private final SecurityService ss;
    private final UserService us;
    private static final Logger LOGGER = LoggerFactory.getLogger(ReservationController.class);

    @Context
    private UriInfo uriInfo;

    @Autowired
    private Environment env;

    @Autowired
    public ReservationController(final ReservationService rs, final SecurityService ss, final UserService us) {
        this.rs = rs;
        this.ss = ss;
        this.us = us;
    }

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public Response readReservations(
            @QueryParam("page") @DefaultValue("1") @Min(value = 1) final int page,
            @QueryParam("userId") final Long owner,
            @QueryParam("past") @DefaultValue("false") final boolean past
    ) {
        LOGGER.debug("Reading reservations for user {}", owner);

        final PagedQuery<Reservation> result = rs.getForUser(owner, page, past);
        if (result.getContent().isEmpty()) {
            return Response.noContent().build();
        }
        final List<ReservationDTO> reservationDTOList = result.getContent()
                .stream().map(r -> ReservationDTO.fromReservation(uriInfo, r))
                .collect(Collectors.toList());
        Response.ResponseBuilder responseBuilder = Response.ok(new GenericEntity<List<ReservationDTO>>(reservationDTOList){});
        return ResponseUtils.addLinksFromPagedQuery(
                result,
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
                getBaseURL() // TODO: Check if this replaces: uriInfo.getRequestURL().toString().replace(request.getServletPath(), "")
        );
        final URI location = uriInfo.getAbsolutePathBuilder().path(String.valueOf(reservation.getId())).build();
        return Response.created(location).build();
    }


    @PATCH
    @Path("/{id}")
    @PreAuthorize("@securityManager.isReservationRestaurant(authentication, #reservationId)")
    public Response updateRestaurantConfirmation(
            @PathParam("id") final long reservationId,
            @Valid ReservationConfirmationForm reservationConfirmationForm
    ) {
        // TODO: Should we support unconfirmation?
        rs.confirm(reservationId, getBaseURL());
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
        rs.delete(reservationId, getBaseURL());
        return Response.noContent().build();
    }

    private String getBaseURL() {
        return env.getProperty("url.base");
    }

}