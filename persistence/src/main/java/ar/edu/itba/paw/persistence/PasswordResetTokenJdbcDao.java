package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.model.PasswordResetToken;
import ar.edu.itba.paw.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
public class PasswordResetTokenJdbcDao implements PasswordResetTokenDao {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;
    static final RowMapper<PasswordResetToken> ROW_MAPPER = (rs, rowNum) ->
            new PasswordResetToken(rs.getLong("id"), rs.getString("token"), rs.getLong("user_id"), rs.getObject("expiry_date", LocalDateTime.class), rs.getBoolean("is_used"));

    @Autowired
    public PasswordResetTokenJdbcDao(final DataSource ds) {
        this.jdbcTemplate = new JdbcTemplate(ds);
        this.jdbcInsert = new SimpleJdbcInsert(ds).withTableName("password_reset_token").usingGeneratedKeyColumns("id");
    }

    @Override
    public Optional<PasswordResetToken> getByUserId(long userId) {
        return jdbcTemplate.query("SELECT * FROM password_reset_token WHERE user_id = ? ORDER BY expiry_date ASC", new Object[]{userId}, ROW_MAPPER).stream().findFirst();
    }

    @Override
    public PasswordResetToken create(String token, User user, LocalDateTime expiryDate, boolean isUsed) {
        final Map<String, Object> passwordResetTokenData = new HashMap<>();
        passwordResetTokenData.put("token", token);
        passwordResetTokenData.put("user_id", user.getId());
        passwordResetTokenData.put("is_used", isUsed);
        passwordResetTokenData.put("expiry_date", expiryDate.plusMinutes(PasswordResetToken.EXPIRATION));
        final long PasswordResetTokenId = jdbcInsert.executeAndReturnKey(passwordResetTokenData).intValue();

        return new PasswordResetToken(PasswordResetTokenId, token, user.getId(), expiryDate, isUsed);
    }

    @Override
    public Optional<PasswordResetToken> getByToken(String token) {
        return jdbcTemplate.query("SELECT * FROM password_reset_token WHERE token = ? ORDER BY expiry_date ASC", new Object[]{token}, ROW_MAPPER).stream().findFirst();
    }

    @Override
    public void setUsed(String token) {
        jdbcTemplate.update("UPDATE password_reset_token SET is_used = true WHERE token = ?", token);
    }
}
