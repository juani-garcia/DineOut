package ar.edu.itba.paw.persistence;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ReservationDao {

    Reservation create(Restaurant restaurant, String userMail, int amount, LocalDateTime dateTime, String comments);

    List<Reservation> getAllFutureByUsername(String username);

    void delete(long reservationId);

    Optional<String> getReservationOwner(long reservationId);

}
