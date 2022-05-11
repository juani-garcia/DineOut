package ar.edu.itba.paw.model;

import java.time.LocalDateTime;

public class PasswordResetToken {
    public static final int EXPIRATION = 60 * 24;

    private final Long id;

    private final String token;

    private final long userId;

    private final LocalDateTime expiryDate;

    private final boolean isUsed;

    public PasswordResetToken(Long id, String token, long userId, LocalDateTime expiryDate, boolean isUsed) {
        this.id = id;
        this.token = token;
        this.userId = userId;
        this.expiryDate = expiryDate;
        this.isUsed = isUsed;
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

    public boolean isUsed() {
        return isUsed;
    }
}
