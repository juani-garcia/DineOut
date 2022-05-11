package ar.edu.itba.paw.persistence;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class RoleToAuthorityJdbcDao implements RoleToAuthorityDao {

    private final JdbcTemplate jdbcTemplate;
    static final RowMapper<RoleToAuthority> ROW_MAPPER = (rs, rowNum) ->
            new RoleToAuthority(rs.getLong("id"), rs.getLong("authority_id"), rs.getLong("role_id"));

    @Autowired
    public RoleToAuthorityJdbcDao(final DataSource ds) {
        jdbcTemplate = new JdbcTemplate(ds);
    }

    @Override
    public List<RoleToAuthority> getByRoleId(long roleId) {
        List<RoleToAuthority> query = jdbcTemplate.query("SELECT * FROM role_to_authority WHERE role_id = ?", new Object[]{roleId}, ROW_MAPPER);
        return query;
    }
}

