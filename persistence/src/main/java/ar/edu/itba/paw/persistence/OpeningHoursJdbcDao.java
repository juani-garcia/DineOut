package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.model.OpeningHours;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class OpeningHoursJdbcDao implements OpeningHoursDao {

    private final JdbcTemplate jdbcTemplate;
    private final static RowMapper<OpeningHours> OPENING_HOURS_ROW_MAPPER = (rs, rowNum) ->
            OpeningHours.getById(rs.getLong("id"));

    @Autowired
    public OpeningHoursJdbcDao(final DataSource ds) {
        this.jdbcTemplate = new JdbcTemplate(ds);
    }

    @Override
    public List<OpeningHours> getByRestaurantId(long restaurantId) {
        String sql = "SELECT opening_hours_id id FROM restaurant_opening_hours WHERE restaurant_id = ?";
        Object[] args = new Object[]{restaurantId};
        return jdbcTemplate.query(sql, args, OPENING_HOURS_ROW_MAPPER);
    }

    @Override
    public boolean add(long restaurantId, OpeningHours openingHours) {
        String sql = "INSERT INTO restaurant_opening_hours VALUES (?, ?) ON CONFLICT DO NOTHING";
        Object[] args = new Object[]{restaurantId, openingHours.getId()};
        return jdbcTemplate.update(sql, args) == 1;
    }

    @Override
    public boolean delete(long restaurantId, OpeningHours openingHours) {
        String sql = "DELETE FROM restaurant_opening_hours WHERE restaurant_id = ? AND opening_hours_id = ?";
        Object[] args = new Object[]{restaurantId, openingHours.getId()};
        return jdbcTemplate.update(sql, args) == 1;
    }
}
