package ar.edu.itba.paw.persistence;

import java.text.DateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Reservation {
    private final long reservationId, restaurantId;
    private final int amount;
    private final LocalDateTime dateTime;
    private final String userMail, comments;
    private Restaurant restaurant;

    protected Reservation(long reservationId, long restaurantId, String userMail, int amount, LocalDateTime dateTime, String comments) {
        this.reservationId = reservationId;
        this.restaurantId = restaurantId;
        this.amount = amount;
        this.dateTime = dateTime;
        this.userMail = userMail;
        this.comments = comments;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
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
}
