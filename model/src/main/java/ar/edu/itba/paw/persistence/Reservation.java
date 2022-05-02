package ar.edu.itba.paw.persistence;

import java.time.LocalDateTime;

public class Reservation {
    private final long reservationId, restaurantId;
    private final int amount;
    private final LocalDateTime dateTime;
    private final String userMail, comments;

    protected Reservation(long reservationId, long restaurantId, String userMail, int amount, LocalDateTime dateTime, String comments) {
        this.reservationId = reservationId;
        this.restaurantId = restaurantId;
        this.amount = amount;
        this.dateTime = dateTime;
        this.userMail = userMail;
        this.comments = comments;
    }

    public long getReservationId() {
        return reservationId;
    }

    public long getRestaurantId() {
        return restaurantId;
    }

    public int getAmount() {
        return amount;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public String getComments() {
        return comments;
    }

    public String getMail() {
        return userMail;
    }
}
