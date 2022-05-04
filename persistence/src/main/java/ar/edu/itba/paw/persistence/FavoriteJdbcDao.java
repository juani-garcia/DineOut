package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.model.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

@Repository
public class FavoriteJdbcDao implements FavoriteDao {

    private final JdbcTemplate jdbcTemplate;
    private static final RowMapper<Favorite> ROW_MAPPER = (rs, rowNum) ->
            new Favorite(rs.getLong("restaurant_id"), rs.getLong("user_id"));

    @Autowired
    public FavoriteJdbcDao(final DataSource ds) {
        this.jdbcTemplate = new JdbcTemplate(ds);
    }

    @Override
    public boolean delete(long restaurantId, long userId) {
        String sql = "DELETE FROM favorite WHERE restaurant_id = ? AND user_id = ?";
        Object[] args = new Object[]{restaurantId, userId};
        return jdbcTemplate.update(sql, args) == 1;
    }

    @Override
    public boolean create(long restaurantId, long userId) {
        String sql = "INSERT INTO favorite VALUES (?, ?) ON CONFLICT DO NOTHING";
        Object[] args = new Object[]{restaurantId, userId};
        return jdbcTemplate.update(sql, args) == 1;
    }

    @Override
    public Long getFavCount(long restaurantId) {
        return null;
    }
}
