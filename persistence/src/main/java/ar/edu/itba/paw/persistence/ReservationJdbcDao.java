package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.model.Reservation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Repository
public class ReservationJdbcDao implements ReservationDao {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;

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
}