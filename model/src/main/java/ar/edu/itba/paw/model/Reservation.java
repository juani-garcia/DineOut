package ar.edu.itba.paw.model;

import java.time.LocalDateTime;

public class Reservation {
    private final long reservationId, restaurantId;
    private final char amount;
    private final LocalDateTime dateTime;
    private final String comments;

    public Reservation(long reservationId, long restaurantId, char amount, LocalDateTime dateTime, String comments) {
        this.reservationId = reservationId;
        this.restaurantId = restaurantId;
        this.amount = amount;
        this.dateTime = dateTime;
        this.comments = comments;
    }

    public long getReservationId() {
        return reservationId;
    }

    public long getRestaurantId() {
        return restaurantId;
    }

    public char getAmount() {
        return amount;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public String getComments() {
        return comments;
    }
}
