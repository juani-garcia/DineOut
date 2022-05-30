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
        String cmp = past ? "<=" : ">";
        String query = "SELECT * FROM " +
                "(SELECT * FROM restaurant, reservation WHERE restaurant.id = reservation.restaurant_id) as rr " +
                "LEFT OUTER JOIN account ON rr.user_mail = account.username " +
                "WHERE username = ? " +
                "AND date_time " + cmp + " now() " +
                "ORDER BY date_time, rr.name LIMIT ? OFFSET ?";

        return jdbcTemplate.query(query, new Object[]{username, PAGE_SIZE, (page - 1) * PAGE_SIZE}, ROW_MAPPER);
    }

    @Override
    public List<Reservation> getAllByRestaurant(long restaurantId, int page, boolean past) {
        String cmp = past ? "<=" : ">";
        String query = "SELECT * FROM " +
                "(SELECT * FROM restaurant, reservation WHERE restaurant.id = reservation.restaurant_id) as rr " +
                "LEFT OUTER JOIN account ON rr.user_mail = account.username " +
                "WHERE rr.id = ?" +
                "AND date_time " + cmp + " now() " +
                "ORDER BY date_time, rr.name LIMIT ? OFFSET ?";

        return jdbcTemplate.query(query, new Object[]{restaurantId, PAGE_SIZE, (page - 1) * PAGE_SIZE}, ROW_MAPPER);
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
    public boolean confirm(final long reservationId) {
        String query = "UPDATE reservation SET is_confirmed = true WHERE reservation_id = ?";
        return jdbcTemplate.update(query, reservationId) == 1;
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
        String cmp = past ? "<=" : ">";
        String query = "SELECT COUNT(*) FROM reservation " +
                "WHERE restaurant_id = ? " +
                "AND date_time " + cmp + " now() ";

        return jdbcTemplate.queryForObject(query, new Object[]{self.getId()}, Long.class);
    }

    private Long getCountForCurrentUser(String username, boolean past) {
        String cmp = past ? "<=" : ">";
        String query = "SELECT COUNT(*) FROM reservation " +
                "WHERE user_mail = ? " +
                "AND date_time " + cmp + " now() ";

        return jdbcTemplate.queryForObject(query, new Object[]{username}, Long.class);
    }
}
