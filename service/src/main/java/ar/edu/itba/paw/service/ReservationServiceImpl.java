package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.Shift;
import ar.edu.itba.paw.persistence.Reservation;
import ar.edu.itba.paw.persistence.ReservationDao;
import ar.edu.itba.paw.model.exceptions.InvalidTimeException;
import ar.edu.itba.paw.persistence.Restaurant;
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

    @Override
    public Reservation create(long restaurantId, String userMail, int amount, LocalDateTime dateTime, String comments) {
        if(!restaurantService.getById(restaurantId).isPresent()) {
            throw new InvalidTimeException();
        }

        if(!Shift.belongs(shiftService.getByRestaurantId(restaurantId), LocalTime.from(dateTime))) {
            throw new InvalidTimeException();
        }

        Reservation reservation = reservationDao.create(restaurantId, userMail, amount, dateTime, comments);

        String restaurantMail =  restaurantService.getById(restaurantId).orElseThrow(RuntimeException::new).getMail();

        emailService.sendReservationToRestaurant(
                reservation.getReservationId(), restaurantMail, userMail, amount, dateTime, comments);

        return reservation;
    }

    @Override
    public List<Reservation> getAllByUsername(String username) {
        List<Reservation> reservationList = reservationDao.getAllByUsername(username);
        for (Reservation reservation :
                reservationList) {
            Optional<Restaurant> restaurant = restaurantService.getById(reservation.getRestaurantId());
            if (!restaurant.isPresent()) throw new IllegalStateException("Restaurant id: " + reservation.getRestaurantId() + " in reservation id: " + reservation.getReservationId() + " is not a valid restaurant");
            reservation.setRestaurant(restaurant.get());
        }
        return reservationList;
    }
}
