package ar.edu.itba.paw.persistence;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class UserToRoleJdbcDao implements UserToRoleDao {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;
    static final RowMapper<UserToRole> ROW_MAPPER = (rs, rowNum) ->
            new UserToRole(rs.getLong("id"), rs.getLong("user_id"), rs.getLong("role_id"));

    @Autowired
    public UserToRoleJdbcDao(final DataSource ds) {
        jdbcTemplate = new JdbcTemplate(ds);
        this.jdbcInsert = new SimpleJdbcInsert(ds).withTableName("account_to_role").usingGeneratedKeyColumns("id");
    }

    @Override
    public List<UserToRole> getByUserId(long userId) {
        List<UserToRole> query = jdbcTemplate.query("SELECT * FROM account_to_role WHERE user_id = ?", new Object[]{userId}, ROW_MAPPER);
        return query;
    }

    @Override
    public UserToRole create(final long userId, final long roleId) {
        final Map<String, Object> userToRoleData = new HashMap<>();
        userToRoleData.put("user_id", userId);
        userToRoleData.put("role_id", roleId);

        final long userToRoleId = jdbcInsert.executeAndReturnKey(userToRoleData).longValue();
        return new UserToRole(userToRoleId, userId, roleId);
    }
}

