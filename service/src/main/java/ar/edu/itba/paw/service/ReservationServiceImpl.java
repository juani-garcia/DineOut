package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.Shift;
import ar.edu.itba.paw.model.exceptions.RestaurantNotFoundException;
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

    @Autowired
    private UserService userService;

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
    public List<Reservation> getAllForCurrentUser(int page, boolean past) {
        String username = securityService.getCurrentUsername();
        if(username == null) throw new IllegalStateException("Not logged in"); // TODO: change for is logged in

        return reservationDao.getAllByUsername(username, page, past);
    }

    @Override
    public List<Reservation> getAllForCurrentRestaurant(int page, boolean past) {
        User user = securityService.getCurrentUser().orElseThrow(() -> new IllegalStateException("Not logged in"));
        Restaurant self = restaurantService.getByUserID(user.getId()).orElseThrow(() -> new IllegalStateException("Invalid restaurant"));

        return reservationDao.getAllByRestaurant(self.getId(), page, past);
    }

    @Override
    public void delete(long reservationId) {
        User user = securityService.getCurrentUser().orElseThrow(UnauthorizedReservationException::new);
        Reservation reservation = reservationDao.getReservation(reservationId).orElseThrow(UnauthorizedReservationException::new);
        Optional<Restaurant> restaurant = restaurantService.getByUserID(user.getId());
        User reservationOwner = userService.getByUsername(reservation.getMail()).orElseThrow(() -> new IllegalStateException("Reservation was made by unkown user"));

        boolean userMadeReservation = reservation.getMail().equals(user.getUsername()) && !restaurant.isPresent();
        boolean reservationWasMadeToRestaurant = restaurant.isPresent() && restaurant.get().getId() == reservation.getRestaurantId();
        boolean reservationIsFuture = reservation.getDateTime().isAfter(LocalDateTime.now());

        if((!userMadeReservation && !reservationWasMadeToRestaurant) || !reservationIsFuture) {
            throw new UnauthorizedReservationException();
        }

        emailService.sendReservationCancelledUser(
                reservation.getMail(), reservationOwner.getFirstName(), reservation);
        emailService.sendReservationCancelledRestaurant(
                reservation.getRestaurant().getMail(), reservation.getRestaurant().getName(), reservation, reservationOwner);

        reservationDao.delete(reservationId);

    }

    @Override
    public boolean confirm(long reservationId) {
        User user = securityService.getCurrentUser().orElseThrow(() -> new IllegalStateException("Not logged in"));
        Restaurant restaurant = restaurantService.getByUserID(user.getId()).orElseThrow(() -> new IllegalStateException("Not a restaurant"));
        Reservation reservation = reservationDao.getReservation(reservationId).orElseThrow(UnauthorizedReservationException::new);

        if(reservation.getRestaurantId() != restaurant.getId() || reservation.getDateTime().isBefore(LocalDateTime.now())) {
            throw new UnauthorizedReservationException();
        }

        return reservationDao.confirm(reservation.getReservationId());
    }
}
