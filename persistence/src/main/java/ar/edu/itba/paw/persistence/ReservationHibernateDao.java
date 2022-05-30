package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.model.Reservation;
import ar.edu.itba.paw.model.Restaurant;
import ar.edu.itba.paw.model.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public class ReservationHibernateDao implements ReservationDao {

    @PersistenceContext
    private EntityManager em;

    private static final int PAGE_SIZE = 8;

    @Override
    public Reservation create(Restaurant restaurant, User user, int amount, LocalDateTime dateTime, String comments) {
        final Reservation reservation = new Reservation(restaurant, user, amount, dateTime, comments);
        em.persist(reservation);
        return reservation;
    }

    @Override
    public List<Reservation> getAllByUsername(String username, int page, boolean past) {
        // TODO
        return null;
    }

    @Override
    public List<Reservation> getAllByRestaurant(long restaurantId, int page, boolean past) {
        // TODO
        return null;
    }

    @Override
    public void delete(final long reservationId) {
        em.remove(em.find(Reservation.class, reservationId));
    }

    @Override
    public Optional<Reservation> getReservation(final long reservationId) {
        return Optional.ofNullable(em.find(Reservation.class, reservationId));
    }

    @Override
    public long getPagesCountForCurrentUser(String username, boolean past) {
        return Double.valueOf(Math.ceil(getCountForCurrentUser(username, past).doubleValue() / PAGE_SIZE)).longValue();
    }

    @Override
    public long getPagesCountForCurrentRestaurant(Restaurant self, boolean past) {
        return Double.valueOf(Math.ceil(getCountForCurrentRestaurant(self, past).doubleValue() / PAGE_SIZE)).longValue();
    }

    private Long getCountForCurrentRestaurant(Restaurant self, boolean past) {
        // TODO
        return null;
    }

    private Long getCountForCurrentUser(String username, boolean past) {
        // TODO
        return null;
    }
}
