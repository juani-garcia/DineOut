package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.model.Reservation;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ReservationDao {

    Reservation create(long restaurantId, String userMail, int amount, LocalDateTime dateTime, String comments);

    List<Reservation> getAllByUsername(String username);

}
