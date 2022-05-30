package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.model.PasswordResetToken;
import ar.edu.itba.paw.model.User;

import java.time.LocalDateTime;
import java.util.Optional;

public interface PasswordResetTokenDao {
    Optional<PasswordResetToken> getByUserId(long userId);

    PasswordResetToken create(String token, User user, LocalDateTime expiryDate);

    Optional<PasswordResetToken> getByToken(String token);

}
