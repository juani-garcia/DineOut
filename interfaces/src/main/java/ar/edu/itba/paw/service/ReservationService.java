package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.PagedQuery;
import ar.edu.itba.paw.model.Reservation;

import java.time.LocalDateTime;

public interface ReservationService {

    Reservation create(long restaurantId, String userMail, int amount, LocalDateTime dateTime, String comments);

    PagedQuery<Reservation> getAllForCurrentUser(int page, boolean past);

    PagedQuery<Reservation> getAllForCurrentRestaurant(int page, boolean past);

    void delete(long reservationId);

    void confirm(long reservationId);

}
