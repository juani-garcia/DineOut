package ar.edu.itba.paw.webapp.dto;

import ar.edu.itba.paw.model.Reservation;
import ar.edu.itba.paw.model.User;

import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.time.LocalDateTime;

public class ReservationDTO {
    private Long id;
    private URI restaurant;
    private URI owner;
    private int amount;
    private LocalDateTime dateTime;
    private String comments;
    private boolean isConfirmed;
    private URI self;

    public static ReservationDTO fromReservation(final UriInfo uriInfo, final Reservation reservation) {
        final ReservationDTO dto = new ReservationDTO();

        dto.id = reservation.getId();
        dto.restaurant = RestaurantDTO.getUriBuilder(uriInfo, reservation.getRestaurant()).build();
        dto.owner = UserDTO.getUriBuilder(uriInfo, reservation.getOwner()).build();
        dto.amount = reservation.getAmount();
        dto.dateTime = reservation.getDateTime();
        dto.comments = reservation.getComments();
        dto.isConfirmed = reservation.getIsConfirmed();
        dto.self = ReservationDTO.getUriBuilder(uriInfo, reservation).build();

        return dto;
    }

    public static UriBuilder getUriBuilder(final UriInfo uriInfo) {
        return uriInfo.getBaseUriBuilder().path("reservations");
    }

    public static UriBuilder getUriBuilder(final UriInfo uriInfo, final Reservation reservation) {
        return ReservationDTO.getUriBuilder(uriInfo).path(String.valueOf(reservation.getId()));
    }

    public static UriBuilder getUriBuilderByUser(final UriInfo uriInfo, final User user) {
        return ReservationDTO.getUriBuilder(uriInfo).queryParam("owner", user.getId());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public URI getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(URI restaurant) {
        this.restaurant = restaurant;
    }

    public URI getOwner() {
        return owner;
    }

    public void setOwner(URI owner) {
        this.owner = owner;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public boolean isConfirmed() {
        return isConfirmed;
    }

    public void setConfirmed(boolean confirmed) {
        isConfirmed = confirmed;
    }

    public URI getSelf() {
        return self;
    }

    public void setSelf(URI self) {
        this.self = self;
    }
}
