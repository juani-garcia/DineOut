package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.Reservation;
import ar.edu.itba.paw.persistence.ReservationDao;
import ar.edu.itba.paw.persistence.RestaurantDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ReservationServiceImpl implements ReservationService {

    private final ReservationDao reservationDao;

    @Autowired
    public ReservationServiceImpl(final ReservationDao reservationDao) {
        this.reservationDao = reservationDao;
    }

    @Override
    public Reservation createReservation(long restaurantId, String userMail, int amount, LocalDateTime dateTime, String comments) {
        if(dateTime.isBefore(LocalDateTime.now())) {
            // Reservation was made in the past.
        }

        return reservationDao.create(restaurantId, userMail, amount, dateTime, comments);
    }
}
