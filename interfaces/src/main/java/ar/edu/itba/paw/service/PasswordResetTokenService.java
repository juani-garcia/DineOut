package ar.edu.itba.paw.service;

import ar.edu.itba.paw.persistence.PasswordResetToken;
import ar.edu.itba.paw.persistence.User;

import java.time.LocalDateTime;
import java.util.Optional;

public interface PasswordResetTokenService {
    PasswordResetToken create(String token, User user, LocalDateTime expiryDate);

    Optional<PasswordResetToken> getByUserId(long userId);

    boolean hasValidToken(long userId);

    Optional<PasswordResetToken> getByToken(String token);
}
