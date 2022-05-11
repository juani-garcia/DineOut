package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.model.Reservation;
import ar.edu.itba.paw.model.Restaurant;
import ar.edu.itba.paw.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class ReservationJdbcDao implements ReservationDao {

    private static final int PAGE_SIZE = 8;
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;
    static final RowMapper<Reservation> ROW_MAPPER = (rs, rowNum) ->
            new Reservation(rs.getLong("reservation_id"),
                    rs.getInt("amount"), rs.getObject("date_time", LocalDateTime.class),
                    rs.getString("comments"),
                    new Restaurant(rs.getLong("id"), rs.getLong("user_id"),
                            rs.getString("name"), null,
                            rs.getString("address"), rs.getString("mail"),
                            null, null, 0L),
                    new User(rs.getLong("id"), rs.getString("username"),
                             null, rs.getString("first_name"), rs.getString("last_name")),
                    rs.getBoolean("is_confirmed"));

    @Autowired
    public ReservationJdbcDao(final DataSource ds) {
        this.jdbcTemplate = new JdbcTemplate(ds);
        this.jdbcInsert = new SimpleJdbcInsert(ds).withTableName("reservation").usingGeneratedKeyColumns("reservation_id");
    }

    @Override
    public Reservation create(Restaurant restaurant, User user, int amount, LocalDateTime dateTime, String comments) {
        final Map<String, Object> reservationData = new HashMap<>();
        reservationData.put("restaurant_id", restaurant.getId());
        reservationData.put("user_mail", user.getUsername());
        reservationData.put("amount", amount);
        reservationData.put("date_time", dateTime);
        reservationData.put("comments", comments);
        reservationData.put("is_confirmed", false);
        final long reservationId = jdbcInsert.executeAndReturnKey(reservationData).intValue();

        return new Reservation(reservationId, amount, dateTime, comments, restaurant, user, false);
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
    public void delete(long reservationId) {
        jdbcTemplate.update("DELETE FROM reservation WHERE reservation_id = ?", reservationId);
    }

    @Override
    public Optional<Reservation> getReservation(long reservationId) {
        String query = "SELECT * FROM " +
                "(SELECT * FROM restaurant, reservation WHERE restaurant.id = reservation.restaurant_id) as rr " +
                "LEFT OUTER JOIN account ON rr.user_mail = account.username " +
                "WHERE reservation_id = ? ";
        return jdbcTemplate.query(query,
                new Object[]{reservationId},
                ROW_MAPPER).stream().findFirst();
    }

    @Override
    public boolean confirm(long reservationId) {
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
