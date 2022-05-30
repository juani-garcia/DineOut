package ar.edu.itba.paw.model;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Entity
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "reservation_reservation_id_seq")
    @SequenceGenerator(allocationSize = 1, sequenceName = "reservation_reservation_id_seq", name = "reservation_reservation_id_seq")
    @Column(name = "reservation_id")
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "restaurant_id", nullable = false)
    private Restaurant restaurant;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_mail", referencedColumnName = "username", nullable = false)
    private User owner;

    @Column(nullable = false)
    private int amount;

    @Column(name = "date_time", nullable = false)
    private LocalDateTime dateTime;

    @Column
    private String comments;

    @Column(name = "is_confirmed", nullable = false)
    private boolean isConfirmed;

    Reservation() {
    }

    public Reservation(Restaurant restaurant, User owner, int amount, LocalDateTime dateTime, String comments) {
        this.restaurant = restaurant;
        this.owner = owner;
        this.amount = amount;
        this.dateTime = dateTime;
        this.comments = comments;
        this.isConfirmed = false;
    }

    @Deprecated
    public Reservation(long reservationId, int amount, LocalDateTime dateTime,
                          String comments, Restaurant restaurant, User owner, boolean isConfirmed) {
        // this.reservationId = reservationId;
        this.amount = amount;
        this.dateTime = dateTime;
        this.owner = owner;
        this.comments = comments;
        this.restaurant = restaurant;
        this.isConfirmed = isConfirmed;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public long getId() {
        return id;
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

    public User getOwner() {
        return owner;
    }

    public String getMail() {
        return getOwner().getUsername();
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
