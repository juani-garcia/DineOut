package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.model.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

@Repository
public class UserRoleJdbcDao implements UserRoleDao {

    private final JdbcTemplate jdbcTemplate;
    static final RowMapper<UserRole> ROW_MAPPER = (rs, rowNum) ->
            new UserRole(rs.getLong("id"), rs.getString("role_name"));

    @Autowired
    public UserRoleJdbcDao(final DataSource ds) {
        jdbcTemplate = new JdbcTemplate(ds);
    }

    @Override
    public Optional<UserRole> getByRoleId(long roleId) {
        List<UserRole> query = jdbcTemplate.query("SELECT * FROM account_role WHERE id = ?", new Object[]{roleId}, ROW_MAPPER);
        return query.stream().findFirst();
    }

    @Override
    public Optional<UserRole> getByRoleName(String roleName) {
        List<UserRole> query = jdbcTemplate.query("SELECT * FROM account_role WHERE role_name = ?", new Object[]{roleName}, ROW_MAPPER);
        return query.stream().findFirst();
    }
}

