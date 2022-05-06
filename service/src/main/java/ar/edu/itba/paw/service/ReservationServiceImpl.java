package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.Shift;
import ar.edu.itba.paw.model.exceptions.RestaurantNotFoundException;
import ar.edu.itba.paw.model.exceptions.UnauthorizedReservationException;
import ar.edu.itba.paw.persistence.Reservation;
import ar.edu.itba.paw.persistence.ReservationDao;
import ar.edu.itba.paw.model.exceptions.InvalidTimeException;
import ar.edu.itba.paw.persistence.Restaurant;
import ar.edu.itba.paw.persistence.User;
import jdk.internal.net.http.ResponseTimerEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
public class ReservationServiceImpl implements ReservationService {

    @Autowired
    private ReservationDao reservationDao;

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private ShiftService shiftService;

    @Autowired
    private SecurityService securityService;

    @Override
    public Reservation create(long restaurantId, String userMail, int amount, LocalDateTime dateTime, String comments) {
        Restaurant restaurant = restaurantService.getById(restaurantId).orElseThrow(RestaurantNotFoundException::new);

        if(!Shift.belongs(shiftService.getByRestaurantId(restaurantId), LocalTime.from(dateTime))) {
            throw new InvalidTimeException();
        }

        Reservation reservation = reservationDao.create(restaurant, userMail, amount, dateTime, comments);

        emailService.sendReservationToRestaurant(
                reservation.getReservationId(), restaurant.getMail(), userMail, amount, dateTime, comments);

        return reservation;
    }

    @Override
    public List<Reservation> getAllFutureByUsername(String username) {
        return reservationDao.getAllFutureByUsername(username);
    }

    @Override
    public void delete(long reservationId) {
        User user = securityService.getCurrentUser().orElseThrow(IllegalStateException::new);
        Optional<String> owner = reservationDao.getReservationOwner(reservationId);

        if(user == null || !owner.isPresent() || !owner.get().equals(user.getUsername())) {
            throw new UnauthorizedReservationException();
        }

        // TODO: send mail telling that reservation was cancelled

        reservationDao.delete(reservationId);

    }
}
