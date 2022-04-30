package ar.edu.itba.paw.persistence;

import java.time.LocalDateTime;
import java.util.List;

public interface ReservationDao {

    Reservation create(long restaurantId, String userMail, int amount, LocalDateTime dateTime, String comments);

    List<Reservation> getAllByUsername(String username);

}
