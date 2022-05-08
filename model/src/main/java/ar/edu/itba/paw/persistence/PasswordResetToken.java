package ar.edu.itba.paw.persistence;

import java.time.LocalDateTime;

public class PasswordResetToken {
    public static final int EXPIRATION = 60 * 24;

    private final Long id;

    private final String token;

    private final long userId;

    private final LocalDateTime expiryDate;

    protected PasswordResetToken(Long id, String token, long userId, LocalDateTime expiryDate) {
        this.id = id;
        this.token = token;
        this.userId = userId;
        this.expiryDate = expiryDate;
    }

    public Long getId() {
        return id;
    }

    public String getToken() {
        return token;
    }

    public long getUserId() {
        return userId;
    }

    public LocalDateTime getExpiryDate() {
        return expiryDate;
    }
}
