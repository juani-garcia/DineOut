package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.Reservation;
import ar.edu.itba.paw.model.User;

public interface EmailService {

    void sendAccountCreationMail(String to, String name);

    void sendReservationCreatedUser(String to, String name, Reservation reservation);

    void sendReservationCreatedRestaurant(String to, String name, Reservation reservation, User user);

    void sendReservationCancelledUser(String to, String name, Reservation reservation);

    void sendReservationCancelledRestaurant(String to, String name, Reservation reservation, User user);

    void sendReservationConfirmed(String to, String name, Reservation reservation);

    void sendChangePassword(String to, String name, String token);
}
