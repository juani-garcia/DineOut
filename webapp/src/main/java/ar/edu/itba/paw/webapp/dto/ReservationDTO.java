package ar.edu.itba.paw.webapp.dto;

import ar.edu.itba.paw.model.Reservation;

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
        // TODO: dto.restaurant = reservation.getRestaurant()
        // TODO: dto.owner
        dto.amount = reservation.getAmount();
        dto.dateTime = reservation.getDateTime();
        dto.comments = reservation.getComments();
        dto.isConfirmed = reservation.getIsConfirmed();
        // TODO: dto.self

        return dto;
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
