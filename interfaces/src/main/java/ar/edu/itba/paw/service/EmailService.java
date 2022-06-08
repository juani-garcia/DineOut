package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.Reservation;
import ar.edu.itba.paw.model.User;

public interface EmailService {

    void sendAccountCreationMail(String to, String name, String contextPath);

    void sendReservationCreatedUser(String to, String name, Reservation reservation, String contextPath);

    void sendReservationCreatedRestaurant(String to, String name, Reservation reservation, User user, String contextPath);

    void sendReservationCancelledUser(String to, String name, Reservation reservation, String contextPath);

    void sendReservationCancelledRestaurant(String to, String name, Reservation reservation, User user, String contextPath);

    void sendReservationConfirmed(String to, String name, Reservation reservation, String contextPath);

    void sendChangePassword(String to, String name, String token, String contextPath);
}
