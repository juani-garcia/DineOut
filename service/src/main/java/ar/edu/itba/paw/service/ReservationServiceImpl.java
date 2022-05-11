package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.Shift;
import ar.edu.itba.paw.model.exceptions.*;
import ar.edu.itba.paw.persistence.Reservation;
import ar.edu.itba.paw.persistence.ReservationDao;
import ar.edu.itba.paw.persistence.Restaurant;
import ar.edu.itba.paw.persistence.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
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
        Restaurant restaurant = restaurantService.getById(restaurantId).orElseThrow(NotFoundException::new);
        User user = securityService.getCurrentUser().orElseThrow(() -> new IllegalStateException("Not logged in"));

        if (!Shift.belongs(shiftService.getByRestaurantId(restaurantId), LocalTime.from(dateTime))) {
            throw new InvalidTimeException();
        }

        Reservation reservation = reservationDao.create(restaurant, user, amount, dateTime, comments);

        LocaleContextHolder.setLocale(LocaleContextHolder.getLocale(), true);

        emailService.sendReservationCreatedUser(user.getUsername(), user.getFirstName(), reservation);
        emailService.sendReservationCreatedRestaurant(restaurant.getMail(), restaurant.getName(), reservation, user);

        return reservation;
    }

    @Override
    public List<Reservation> getAllForCurrentUser(int page, boolean past) {
        String username = securityService.getCurrentUsername();
        if (username == null) throw new UnauthenticatedUserException();

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
        User user = securityService.getCurrentUser().orElseThrow(UnauthenticatedUserException::new);
        Reservation reservation = reservationDao.getReservation(reservationId).orElseThrow(NotFoundException::new);
        Optional<Restaurant> restaurant = restaurantService.getByUserID(user.getId());
        User owner = reservation.getOwner();

        boolean userMadeReservation = reservation.getMail().equals(user.getUsername()) && !restaurant.isPresent();
        boolean reservationWasMadeToRestaurant = restaurant.isPresent() && restaurant.get().getId() == reservation.getRestaurantId();
        boolean reservationIsFuture = reservation.getDateTime().isAfter(LocalDateTime.now());

        if ((!userMadeReservation && !reservationWasMadeToRestaurant) || !reservationIsFuture) {
            throw new ForbiddenActionException();
        }

        LocaleContextHolder.setLocale(LocaleContextHolder.getLocale(), true);

        emailService.sendReservationCancelledUser(
                reservation.getMail(), owner == null? "" : owner.getFirstName(), reservation);
        emailService.sendReservationCancelledRestaurant(
                reservation.getRestaurant().getMail(), reservation.getRestaurant().getName(), reservation, owner);

        reservationDao.delete(reservationId);

    }

    @Override
    public boolean confirm(long reservationId) {

        User user = securityService.getCurrentUser().orElseThrow(UnauthenticatedUserException::new);
        Restaurant restaurant = restaurantService.getByUserID(user.getId()).orElseThrow(ForbiddenActionException::new);
        Reservation reservation = reservationDao.getReservation(reservationId).orElseThrow(NotFoundException::new);

        if (reservation.getRestaurantId() != restaurant.getId() || reservation.getDateTime().isBefore(LocalDateTime.now())) {
            throw new InvalidTimeException();
        }

        LocaleContextHolder.setLocale(LocaleContextHolder.getLocale(), true);

        User owner = reservation.getOwner();
        emailService.sendReservationConfirmed(reservation.getMail(),
                owner == null? "" : owner.getFirstName(), reservation);

        return reservationDao.confirm(reservation.getReservationId());
    }

    @Override
    public long getPagesCountForCurrentUser(boolean past) {
        String username = securityService.getCurrentUsername();
        if (username == null) throw new UnauthenticatedUserException();

        return reservationDao.getPagesCountForCurrentUser(username, past);
    }

    @Override
    public long getPagesCountForCurrentRestaurant(boolean past) {
        User user = securityService.getCurrentUser().orElseThrow(UnauthenticatedUserException::new);
        Restaurant self = restaurantService.getByUserID(user.getId()).orElseThrow(() -> new IllegalStateException("Invalid restaurant"));

        return reservationDao.getPagesCountForCurrentRestaurant(self, past);
    }
}
