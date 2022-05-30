package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.model.Reservation;
import ar.edu.itba.paw.model.Restaurant;
import ar.edu.itba.paw.model.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ReservationDao {

    Reservation create(Restaurant restaurant, User user, int amount, LocalDateTime dateTime, String comments);

    List<Reservation> getAllByUsername(String username, int page, boolean past);

    List<Reservation> getAllByRestaurant(long restaurantId, int page, boolean past);

    void delete(long reservationId);

    Optional<Reservation> getReservation(long reservationId);

    long getPagesCountForCurrentUser(String username, boolean past);

    long getPagesCountForCurrentRestaurant(Restaurant self, boolean past);
}
