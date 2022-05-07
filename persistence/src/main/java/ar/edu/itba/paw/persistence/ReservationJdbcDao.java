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
                            Zone.getById(rs.getLong("zone_id"))));
    static final RowMapper<String> OWNER_MAPPER = (rs, rowNum) ->
            rs.getString("user_mail");

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
        final long reservationId = jdbcInsert.executeAndReturnKey(reservationData).intValue();

        return new Reservation(reservationId, userMail, amount, dateTime, comments, restaurant);
    }

    @Override
    public List<Reservation> getAllByUsername(String username, int page, boolean past) {
        String cmp = past? "<=" : ">";
        String query = "SELECT * FROM reservation, restaurant " +
                "WHERE reservation.restaurant_id = restaurant.id " +
                "AND reservation.user_mail = ? " +
                "AND date_time " + cmp + " now() " +
                "ORDER BY date_time LIMIT ? OFFSET ?";

        return jdbcTemplate.query(query, new Object[]{username, PAGE_SIZE, (page - 1) * PAGE_SIZE}, ROW_MAPPER);
    }

    @Override
    public void delete(long reservationId) {
        jdbcTemplate.update("DELETE FROM reservation WHERE reservation_id = ?", reservationId);
    }

    @Override
    public Optional<String> getReservationOwner(long reservationId) {
        List<String> owner = jdbcTemplate.query("SELECT user_mail FROM reservation WHERE reservation_id = ?",
                new Object[]{reservationId},
                OWNER_MAPPER);
        return owner.stream().findFirst();
    }
}
