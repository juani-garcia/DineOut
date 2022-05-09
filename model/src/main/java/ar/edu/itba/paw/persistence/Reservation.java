package ar.edu.itba.paw.persistence;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Reservation {
    private final long reservationId;
    private final int amount;
    private final LocalDateTime dateTime;
    private final String userMail, comments;
    private final Restaurant restaurant;
    private final boolean isConfirmed;

    protected Reservation(long reservationId, String userMail, int amount, LocalDateTime dateTime,
                          String comments, Restaurant restaurant, boolean isConfirmed) {
        this.reservationId = reservationId;
        this.amount = amount;
        this.dateTime = dateTime;
        this.userMail = userMail;
        this.comments = comments;
        this.restaurant = restaurant;
        this.isConfirmed = isConfirmed;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public long getReservationId() {
        return reservationId;
    }

    public long getRestaurantId() {
        return restaurant.getId();
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

    public String getDateString() {
        LocalDate date = LocalDate.from(dateTime);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return date.format(formatter);
    }

    public String getTimeString() {
        LocalTime time = LocalTime.from(dateTime);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        return time.format(formatter);
    }

    public boolean getIsConfirmed() {
        return isConfirmed;
    }
}
