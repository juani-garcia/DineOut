package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.*;
import ar.edu.itba.paw.model.exceptions.*;
import ar.edu.itba.paw.persistence.ReservationDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Locale;
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
    private SecurityService securityService;

    @Transactional
    @Override
    public Reservation create(long restaurantId, String userMail, int amount, LocalDateTime dateTime, String comments, String contextPath) {
        Restaurant restaurant = restaurantService.getById(restaurantId).orElseThrow(NotFoundException::new);
        User user = securityService.getCurrentUser().orElseThrow(() -> new IllegalStateException("Not logged in"));

        if (!Shift.belongs(restaurant.getShifts(), LocalTime.from(dateTime))) {
            throw new InvalidTimeException();
        }

        Reservation reservation = reservationDao.create(restaurant, user, amount, dateTime, comments);

        User restaurantUser = restaurant.getUser();

        emailService.sendReservationCreatedUser(user.getUsername(), user.getFirstName(), reservation, contextPath, user.getLocale());
        emailService.sendReservationCreatedRestaurant(restaurant.getMail(), restaurant.getName(), reservation, user, contextPath,
                restaurantUser != null? restaurantUser.getLocale() : null);

        return reservation;
    }

    @Override
    public Optional<Reservation> getById(final long id) {
        return reservationDao.getReservation(id);
    }

    @Override
    public PagedQuery<Reservation> getAllForCurrentUser(int page, boolean past) {
        String username = securityService.getCurrentUsername();
        if (username == null) throw new UnauthenticatedUserException();

        return reservationDao.getAllByUsername(username, page, past);
    }

    @Override
    public PagedQuery<Reservation> getAllForCurrentRestaurant(int page, boolean past) {
        if (page <= 0) throw new InvalidPageException();

        User user = securityService.getCurrentUser().orElseThrow(() -> new IllegalStateException("Not logged in"));
        Restaurant self = restaurantService.getByUserID(user.getId()).orElseThrow(() -> new IllegalStateException("Invalid restaurant"));

        return reservationDao.getAllByRestaurant(self.getId(), page, past);
    }

    @Transactional
    @Override
    public void delete(long reservationId, String contextPath) {
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

        emailService.sendReservationCancelledUser(
                reservation.getMail(), owner == null? "" : owner.getFirstName(), reservation, contextPath,
                owner == null? null : owner.getLocale());

        User restaurantUser = reservation.getRestaurant().getUser();
        emailService.sendReservationCancelledRestaurant(
                reservation.getRestaurant().getMail(), reservation.getRestaurant().getName(), reservation, owner, contextPath,
                restaurantUser == null? null : restaurantUser.getLocale());

        reservationDao.delete(reservationId);

    }

    @Transactional
    @Override
    public void confirm(final long reservationId, String contextPath) {
        final User user = securityService.getCurrentUser().orElseThrow(UnauthenticatedUserException::new);
        Reservation reservation = reservationDao.getReservation(reservationId).orElseThrow(NotFoundException::new);
        if (!reservation.getRestaurant().getUser().equals(user))
            throw new ForbiddenActionException();

        if (reservation.getDateTime().isBefore(LocalDateTime.now()))
            throw new InvalidTimeException();

        User owner = reservation.getOwner();
        emailService.sendReservationConfirmed(reservation.getMail(),
                owner == null? "" : owner.getFirstName(), reservation, contextPath,
                owner == null? null : owner.getLocale());

        reservation.confirm();
    }

}
