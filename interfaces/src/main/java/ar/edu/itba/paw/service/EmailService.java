package ar.edu.itba.paw.service;

import ar.edu.itba.paw.persistence.PasswordResetToken;
import ar.edu.itba.paw.persistence.User;

import java.time.LocalDateTime;

public interface EmailService {

    void sendReservationToRestaurant(long reservation_id, String to, String from, int amount, LocalDateTime when,  String comments);

    void sendPasswordResetTokenEmail(String contextPath, PasswordResetToken passwordResetToken, User user);
}
