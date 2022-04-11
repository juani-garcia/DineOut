package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.Reservation;
import ar.edu.itba.paw.persistence.ReservationDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ReservationServiceImpl implements ReservationService {

    @Autowired
    private ReservationDao reservationDao;

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private EmailService emailService;

    @Override
    public Reservation create(long restaurantId, String userMail, int amount, LocalDateTime dateTime, String comments) {
        if(dateTime.isBefore(LocalDateTime.now())) {
            // Reservation was made in the past.
        }

        Reservation reservation = reservationDao.create(restaurantId, userMail, amount, dateTime, comments);

        String restaurantMail =  restaurantService.getById(restaurantId).orElseThrow(RuntimeException::new).getMail();

        emailService.sendReservationToRestaurant(
                reservation.getReservationId(), restaurantMail, userMail, amount, dateTime, comments);

        return reservation;
    }
}
