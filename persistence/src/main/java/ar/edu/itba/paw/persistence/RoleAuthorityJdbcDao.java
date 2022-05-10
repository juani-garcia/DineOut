package ar.edu.itba.paw.persistence;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

@Repository
public class RoleAuthorityJdbcDao implements RoleAuthorityDao {

    private final JdbcTemplate jdbcTemplate;
    static final RowMapper<RoleAuthority> ROW_MAPPER = (rs, rowNum) -> new RoleAuthority(rs.getLong("id"), rs.getString("authority_name"));

    @Autowired
    public RoleAuthorityJdbcDao(final DataSource ds) {
        jdbcTemplate = new JdbcTemplate(ds);
    }

    @Override
    public Optional<RoleAuthority> getByAuthorityId(long authorityId) {
        List<RoleAuthority> query = jdbcTemplate.query("SELECT * FROM role_authorities WHERE id = ?", new Object[]{authorityId}, ROW_MAPPER);
        return query.stream().findFirst();
    }
}

