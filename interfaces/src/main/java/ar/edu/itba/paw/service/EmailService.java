package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.Reservation;
import ar.edu.itba.paw.model.User;

import java.util.Locale;

public interface EmailService {

    void sendAccountCreationMail(String to, String name, String contextPath, Locale locale);

    void sendReservationCreatedUser(String to, String name, Reservation reservation, String contextPath, Locale locale);

    void sendReservationCreatedRestaurant(String to, String name, Reservation reservation, User user, String contextPath, Locale locale);

    void sendReservationCancelledUser(String to, String name, Reservation reservation, String contextPath, Locale locale);

    void sendReservationCancelledRestaurant(String to, String name, Reservation reservation, User user, String contextPath, Locale locale);

    void sendReservationConfirmed(String to, String name, Reservation reservation, String contextPath, Locale locale);

    void sendChangePassword(String to, String name, String token, String contextPath, Locale locale);
}
