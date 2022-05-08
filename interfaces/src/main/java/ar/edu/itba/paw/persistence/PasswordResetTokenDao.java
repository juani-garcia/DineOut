package ar.edu.itba.paw.persistence;

import java.time.LocalDateTime;
import java.util.Optional;

public interface PasswordResetTokenDao {
    Optional<PasswordResetToken> getByUserId(long userId);

    PasswordResetToken create(String token, User user, LocalDateTime expiryDate, boolean isUsed);

    Optional<PasswordResetToken> getByToken(String token);

    void setUsed(String token);
}
