package ar.edu.itba.paw.persistence;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ReservationDao {

    Reservation create(Restaurant restaurant, String userMail, int amount, LocalDateTime dateTime, String comments);

    List<Reservation> getAllByUsername(String username, int page, boolean past);

    List<Reservation> getAllByRestaurant(long restaurantId, int page, boolean past);

    void delete(long reservationId);

    Optional<Reservation> getReservation(long reservationId);

    boolean confirm(long reservationId);
}
