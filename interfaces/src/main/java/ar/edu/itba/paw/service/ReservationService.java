package ar.edu.itba.paw.service;

import ar.edu.itba.paw.persistence.Reservation;

import java.time.LocalDateTime;
import java.util.List;

public interface ReservationService {

    Reservation create(long restaurantId, String userMail, int amount, LocalDateTime dateTime, String comments);

    List<Reservation> getAllByUsername(String username, int page, boolean past);

    void delete(long reservationId);
}
