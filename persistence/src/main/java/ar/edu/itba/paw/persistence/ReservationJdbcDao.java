package ar.edu.itba.paw.persistence;

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

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;
    static final RowMapper<Reservation> ROW_MAPPER = (rs, rowNum) ->
            new Reservation(rs.getLong("reservation_id"), rs.getLong("restaurant_id"), rs.getString("user_mail"), rs.getInt("amount"),
                    rs.getObject("date_time", LocalDateTime.class), rs.getString("comments"));
    static final RowMapper<String> OWNER_MAPPER = (rs, rowNum) ->
            rs.getString("user_mail");

    @Autowired
    public ReservationJdbcDao(final DataSource ds) {
        this.jdbcTemplate = new JdbcTemplate(ds);
        this.jdbcInsert = new SimpleJdbcInsert(ds).withTableName("reservation").usingGeneratedKeyColumns("reservation_id");
    }

    @Override
    public Reservation create(long restaurantId, String userMail, int amount, LocalDateTime dateTime, String comments) {
        final Map<String, Object> reservationData = new HashMap<>();
        reservationData.put("restaurant_id", restaurantId);
        reservationData.put("user_mail", userMail);
        reservationData.put("amount", amount);
        reservationData.put("date_time", dateTime);
        reservationData.put("comments", comments);
        final int reservationId = jdbcInsert.executeAndReturnKey(reservationData).intValue();

        return new Reservation(reservationId, restaurantId, userMail, amount, dateTime, comments);
    }

    @Override
    public List<Reservation> getAllByUsername(String username) {
        return jdbcTemplate.query("SELECT * FROM reservation WHERE user_mail = ? ORDER BY date_time", new Object[] {username},  ROW_MAPPER);
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
