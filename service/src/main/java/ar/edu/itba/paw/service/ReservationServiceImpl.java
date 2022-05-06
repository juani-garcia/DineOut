package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.Shift;
import ar.edu.itba.paw.model.exceptions.UnauthorizedReservationException;
import ar.edu.itba.paw.persistence.Reservation;
import ar.edu.itba.paw.persistence.ReservationDao;
import ar.edu.itba.paw.model.exceptions.InvalidTimeException;
import ar.edu.itba.paw.persistence.Restaurant;
import ar.edu.itba.paw.persistence.User;
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
    public long create(long restaurantId, String userMail, int amount, LocalDateTime dateTime, String comments) {
        if(!restaurantService.getById(restaurantId).isPresent()) {
            throw new InvalidTimeException();
        }

        if(!Shift.belongs(shiftService.getByRestaurantId(restaurantId), LocalTime.from(dateTime))) {
            throw new InvalidTimeException();
        }

        long reservationId = reservationDao.create(restaurantId, userMail, amount, dateTime, comments);

        String restaurantMail =  restaurantService.getById(restaurantId).orElseThrow(RuntimeException::new).getMail();

        emailService.sendReservationToRestaurant(
                reservationId, restaurantMail, userMail, amount, dateTime, comments);

        return reservationId;
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
