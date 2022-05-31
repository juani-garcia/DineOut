package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.model.PagedQuery;
import ar.edu.itba.paw.model.Reservation;
import ar.edu.itba.paw.model.Restaurant;
import ar.edu.itba.paw.model.User;

import java.time.LocalDateTime;
import java.util.Optional;

public interface ReservationDao {

    Reservation create(Restaurant restaurant, User user, int amount, LocalDateTime dateTime, String comments);

    Optional<Reservation> getReservation(long reservationId);

    PagedQuery<Reservation> getAllByUsername(String username, int page, boolean past);

    PagedQuery<Reservation> getAllByRestaurant(long restaurantId, int page, boolean past);

    void delete(long reservationId);

}
