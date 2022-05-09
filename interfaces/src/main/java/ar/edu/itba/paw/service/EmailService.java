package ar.edu.itba.paw.service;

import ar.edu.itba.paw.persistence.Reservation;
import ar.edu.itba.paw.persistence.User;

import java.time.LocalDateTime;

public interface EmailService {

    void sendReservationToRestaurant(long reservation_id, String to, String from, int amount, LocalDateTime when,  String comments);

    void sendAccountCreationMail(String to, String name);

    void sendReservationCancelledUser(String to, String name, Reservation reservation);

    void sendReservationCancelledRestaurant(String to, String name, Reservation reservation, User user);
}
