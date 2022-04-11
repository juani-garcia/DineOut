package ar.edu.itba.paw.service;

import java.time.LocalDateTime;

public interface EmailService {

    void sendReservationToRestaurant(long reservation_id, String to, String from, int amount, LocalDateTime when,  String comments);

}
