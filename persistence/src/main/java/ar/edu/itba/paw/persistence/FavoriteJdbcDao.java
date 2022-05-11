package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.model.Favorite;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class FavoriteJdbcDao implements FavoriteDao {

    private final JdbcTemplate jdbcTemplate;
    private static final int PAGE_SIZE = 8;
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

    @Override
    public boolean isFavorite(long restaurantId, long userId) {
        return jdbcTemplate.queryForObject("SELECT COUNT(*) FROM favorite WHERE restaurant_id = ? AND user_id = ?", new Object[]{restaurantId, userId}, Long.class) > 0;
    }

    @Override
    public List<Favorite> getAllByUserId(long userId, int page) {
        return jdbcTemplate.query("SELECT * FROM favorite WHERE user_id = ? LIMIT ? OFFSET ?", new Object[]{userId, PAGE_SIZE, (page - 1) * PAGE_SIZE}, ROW_MAPPER);
    }

    @Override
    public long countByUserId(long id) {
        return jdbcTemplate.queryForObject("SELECT COUNT(*) FROM favorite WHERE user_id = ?", new Object[]{id}, Long.class);
    }

    @Override
    public long countPagesByUserId(long id) {
        return Double.valueOf(Math.ceil(Long.valueOf(countByUserId(id)).doubleValue() / PAGE_SIZE)).longValue();
    }
}
