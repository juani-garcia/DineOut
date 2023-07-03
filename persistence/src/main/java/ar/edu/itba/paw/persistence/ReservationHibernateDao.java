package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.model.PagedQuery;
import ar.edu.itba.paw.model.Reservation;
import ar.edu.itba.paw.model.Restaurant;
import ar.edu.itba.paw.model.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class ReservationHibernateDao implements ReservationDao {

    @PersistenceContext
    private EntityManager em;

    private static final int PAGE_SIZE = 5;

    @Override
    public Reservation create(final Restaurant restaurant,
                              final User user, final int amount,
                              final LocalDateTime dateTime, final String comments) {
        final Reservation reservation = new Reservation(restaurant, user, amount, dateTime, comments);
        em.persist(reservation);
        return reservation;
    }

    @Override
    public Optional<Reservation> getReservation(final long reservationId) {
        return Optional.ofNullable(em.find(Reservation.class, reservationId));
    }

    @Override
    public PagedQuery<Reservation> getAllByUsername(final String username,
                                                    final int page, final boolean past) {
        String cmp = past ? "<=" : ">";
        String baseQuery = "FROM (SELECT * FROM restaurant, reservation WHERE restaurant.id = reservation.restaurant_id) as rr " +
                "LEFT OUTER JOIN account ON rr.user_mail = account.username " +
                "WHERE username = :username " +
                "AND date_time " + cmp + " now() ";

        String idsQuery = "SELECT rr.reservation_id " + baseQuery +
                "ORDER BY rr.date_time " + (past ? "DESC" : "ASC") + ", rr.name " +
                "LIMIT :limit OFFSET :offset";
        Query query = em.createNativeQuery(idsQuery);
        query.setParameter("username", username);
        query.setParameter("limit", PAGE_SIZE);
        query.setParameter("offset", PAGE_SIZE * (page - 1));
        final List<Long> ids = new ArrayList<>();
        for(Object o : query.getResultList()) {
            ids.add(((Number) o).longValue());
        }

        String countQuery = "SELECT COUNT(*) " + baseQuery;
        query = em.createNativeQuery(countQuery);
        query.setParameter("username", username);
        @SuppressWarnings("unchecked")
        long count = ((BigInteger) query.getResultList().stream().findFirst().orElse(0)).longValue();

        if (ids.isEmpty())
            return new PagedQuery<>(new ArrayList<>(), (long) page, (count+PAGE_SIZE-1)/PAGE_SIZE);

        final TypedQuery<Reservation> reservations =
                em.createQuery("from Reservation as r where r.id IN :ids order by dateTime " + (past ? "desc" : "asc"), Reservation.class);
        reservations.setParameter("ids", ids);

        return new PagedQuery<>(reservations.getResultList(), (long) page, (count+PAGE_SIZE-1)/PAGE_SIZE);
    }

    @Override
    public PagedQuery<Reservation> getAllByRestaurant(final long restaurantId,
                                                      final int page, final boolean past)  {
        String cmp = past ? "<=" : ">";
        String baseQuery = "FROM (SELECT * FROM restaurant, reservation WHERE restaurant.id = reservation.restaurant_id) as rr " +
                "LEFT OUTER JOIN account ON rr.user_mail = account.username " +
                "WHERE rr.id = :restaurantId " +
                "AND date_time " + cmp + " now() ";

        String idsQuery = "SELECT rr.reservation_id " +
                baseQuery +
                "ORDER BY date_time " + (past ? "DESC" : "ASC") + ", rr.name LIMIT :limit OFFSET :offset";
        Query query = em.createNativeQuery(idsQuery);
        query.setParameter("restaurantId", restaurantId);
        query.setParameter("limit", PAGE_SIZE);
        query.setParameter("offset", PAGE_SIZE * (page - 1));
        final List<Long> ids = new ArrayList<>();
        for(Object o : query.getResultList()) {
            ids.add(((Number) o).longValue());
        }

        String countQuery = "SELECT COUNT(*) " + baseQuery;
        query = em.createNativeQuery(countQuery);
        query.setParameter("restaurantId", restaurantId);
        @SuppressWarnings("unchecked")
        long count = ((BigInteger) query.getResultList().stream().findFirst().orElse(0)).longValue();

        if (ids.isEmpty())
            return new PagedQuery<>(new ArrayList<>(), (long) page, (count+PAGE_SIZE-1)/PAGE_SIZE);

        final TypedQuery<Reservation> reservations =
                em.createQuery("from Reservation as r where r.id IN :ids order by dateTime " + (past ? "desc" : "asc"), Reservation.class);
        reservations.setParameter("ids", ids);

        return new PagedQuery<>(reservations.getResultList(), (long) page, (count+PAGE_SIZE-1)/PAGE_SIZE);
    }

    @Override
    public void delete(final long reservationId) {
        em.remove(em.find(Reservation.class, reservationId));
    }

}
