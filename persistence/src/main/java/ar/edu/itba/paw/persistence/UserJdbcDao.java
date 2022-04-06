package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

@Repository
public class UserJdbcDao implements UserDao {

    private final JdbcTemplate jdbcTemplate;
    private static final RowMapper<User> ROW_MAPPER = (rs, rowNum) -> new User(rs.getLong("userId"), rs.getString("username"), rs.getString("password"));

    @Autowired
    public UserJdbcDao(final DataSource ds) {
        jdbcTemplate = new JdbcTemplate(ds);
    }

    @Override
    public Optional<User> getUserbyId(long id) {
        // RowMapper o ResultsetExtractor
        List<User> query = jdbcTemplate.query("SELECT * FROM User WHERE userId = ?", new Object[]{id}, ROW_MAPPER);
        return query.stream().findFirst();
    }

    public List<User> getAll(int page) {
        return jdbcTemplate.query("SELECT * FROM User LIMIT 10 OFFSET ?", new Object[]{(page - 1) * 10}, ROW_MAPPER);
    }

}
