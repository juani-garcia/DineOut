package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.model.Zone;
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

    private static final int PAGE_SIZE = 10;
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;
    static final RowMapper<Reservation> ROW_MAPPER = (rs, rowNum) ->
            new Reservation(rs.getLong("reservation_id"), rs.getString("user_mail"),
                    rs.getInt("amount"), rs.getObject("date_time", LocalDateTime.class),
                    rs.getString("comments"),
                    new Restaurant(rs.getLong("id"), rs.getLong("user_id"),
                            rs.getString("name"), rs.getString("address"),
                            rs.getString("mail"), rs.getString("detail"),
                            Zone.getById(rs.getLong("zone_id"))),
                    rs.getBoolean("is_confirmed"));

    @Autowired
    public ReservationJdbcDao(final DataSource ds) {
        this.jdbcTemplate = new JdbcTemplate(ds);
        this.jdbcInsert = new SimpleJdbcInsert(ds).withTableName("reservation").usingGeneratedKeyColumns("reservation_id");
    }

    @Override
    public Reservation create(Restaurant restaurant, String userMail, int amount, LocalDateTime dateTime, String comments) {
        final Map<String, Object> reservationData = new HashMap<>();
        reservationData.put("restaurant_id", restaurant.getId());
        reservationData.put("user_mail", userMail);
        reservationData.put("amount", amount);
        reservationData.put("date_time", dateTime);
        reservationData.put("comments", comments);
        reservationData.put("is_confirmed", false);
        final long reservationId = jdbcInsert.executeAndReturnKey(reservationData).intValue();

        return new Reservation(reservationId, userMail, amount, dateTime, comments, restaurant, false);
    }

    @Override
    public List<Reservation> getAllByUsername(String username, int page, boolean past) {
        String cmp = past? "<=" : ">";
        String query = "SELECT * FROM reservation, restaurant " +
                "WHERE reservation.restaurant_id = restaurant.id " +
                "AND reservation.user_mail = ? " +
                "AND date_time " + cmp + " now() " +
                "ORDER BY date_time, restaurant.name LIMIT ? OFFSET ?";

        return jdbcTemplate.query(query, new Object[]{username, PAGE_SIZE, (page - 1) * PAGE_SIZE}, ROW_MAPPER);
    }

    @Override
    public List<Reservation> getAllByRestaurant(long restaurantId, int page, boolean past) {
        String cmp = past? "<=" : ">";
        String query = "SELECT * FROM reservation, restaurant " +
                "WHERE reservation.restaurant_id = restaurant.id " +
                "AND restaurant.id = ? " +
                "AND date_time " + cmp + " now() " +
                "ORDER BY date_time, restaurant.name LIMIT ? OFFSET ?";

        return jdbcTemplate.query(query, new Object[]{restaurantId, PAGE_SIZE, (page - 1) * PAGE_SIZE}, ROW_MAPPER);
    }

    @Override
    public void delete(long reservationId) {
        jdbcTemplate.update("DELETE FROM reservation WHERE reservation_id = ?", reservationId);
    }

    @Override
    public Optional<Reservation> getReservation(long reservationId) {
        return jdbcTemplate.query("SELECT * FROM reservation, restaurant WHERE restaurant_id = id AND reservation_id = ?",
                new Object[]{reservationId},
                ROW_MAPPER).stream().findFirst();
    }

    @Override
    public boolean confirm(long reservationId) {
        String query = "UPDATE reservation SET is_confirmed = true WHERE reservation_id = ?";
        return jdbcTemplate.update(query, reservationId) == 1;
    }
}
