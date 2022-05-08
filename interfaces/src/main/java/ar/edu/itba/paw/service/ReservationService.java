package ar.edu.itba.paw.service;

import ar.edu.itba.paw.persistence.Reservation;

import java.time.LocalDateTime;
import java.util.List;

public interface ReservationService {

    Reservation create(long restaurantId, String userMail, int amount, LocalDateTime dateTime, String comments);

    List<Reservation> getAllForCurrentUser(int page, boolean past);

    List<Reservation> getAllForCurrentRestaurant(int page, boolean past);

    void delete(long reservationId);

    boolean confirm(long reservationId);

    long getPagesCountForCurrentUser(boolean past);

    long getPagesCountForCurrentRestaurant(boolean past);
}
