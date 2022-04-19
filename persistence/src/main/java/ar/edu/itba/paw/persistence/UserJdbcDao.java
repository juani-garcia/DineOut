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
public class UserJdbcDao implements UserDao {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;
    /* private X default=package-private for testing */
    static final RowMapper<User> ROW_MAPPER = (rs, rowNum) ->
            new User(rs.getLong("id"), rs.getString("username"), rs.getString("password"));

    @Autowired
    public UserJdbcDao(final DataSource ds) {
        jdbcTemplate = new JdbcTemplate(ds);
        this.jdbcInsert = new SimpleJdbcInsert(ds).withTableName("account").usingGeneratedKeyColumns("id");
    }

    @Override
    public Optional<User> getById(final long id) {
        List<User> query = jdbcTemplate.query("SELECT * FROM account WHERE id = ?", new Object[]{id}, ROW_MAPPER);
        return query.stream().findFirst();
    }

    @Override
    public Optional<User> getByUsername(final String username) {
        List<User> query = jdbcTemplate.query("SELECT * FROM account WHERE username = ?", new Object[]{username}, ROW_MAPPER);
        return query.stream().findFirst();
    }

    @Override
    public User create(final String username, final String password) {
        final Map<String, Object> userData = new HashMap<>();
        userData.put("username", username);
        userData.put("password", password);

        final long userId = jdbcInsert.executeAndReturnKey(userData).longValue();
        return new User(userId, username, password);
    }

}