package ar.edu.itba.paw.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "password_reset_token")
public class PasswordResetToken {

    public static final int EXPIRATION = 60 * 24;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "password_reset_token_id_seq")
    @SequenceGenerator(allocationSize = 1, sequenceName = "password_reset_token_id_seq", name = "password_reset_token_id_seq")
    private Long id;

    @Column(nullable = false)
    private String token;

    // TODO: Check orphanRemoval
    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "expiry_date", nullable = false)
    private LocalDateTime expiryDate;

    @Column(name = "is_used", nullable = false)
    private boolean isUsed;

    PasswordResetToken() {
    }

    public PasswordResetToken(String token, User user, LocalDateTime expiryDate) {
        this.token = token;
        this.user = user;
        this.expiryDate = expiryDate;
        this.isUsed = false;
    }

    @Deprecated
    public PasswordResetToken(Long id, String token, long userId, LocalDateTime expiryDate, boolean isUsed) {
        this.id = id;
        this.token = token;
        // this.userId = userId;
        this.expiryDate = expiryDate;
        this.isUsed = isUsed;
    }

    public Long getId() {
        return id;
    }

    public String getToken() {
        return token;
    }

    public User getUser() {
        return user;
    }

    public LocalDateTime getExpiryDate() {
        return expiryDate;
    }

    public boolean isUsed() {
        return isUsed;
    }

}
