package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.PasswordResetToken;
import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.model.exceptions.ForbiddenActionException;
import ar.edu.itba.paw.model.exceptions.UnauthorizedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

@Service
public class SecurityServiceImpl implements SecurityService {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordResetTokenService passwordResetTokenService;

    public String getCurrentUsername() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        if (securityContext != null) {
            Authentication authentication = securityContext.getAuthentication();
            if (authentication != null) return authentication.getName();
        }
        return null;
    }

    @Override
    public Optional<User> getCurrentUser() {
        String username = getCurrentUsername();

        if (username == null) return Optional.empty();

        return userService.getByUsername(getCurrentUsername());
    }

    @Override
    public User checkCurrentUser(Long userId) {
        Optional<User> maybeUser = getCurrentUser();
        if(!maybeUser.isPresent()) throw new UnauthorizedException();
        if(!Objects.equals(maybeUser.get().getId(), userId)) throw new ForbiddenActionException();
        return maybeUser.get();
    }

    @Override
    public boolean isLoggedIn() {
        return SecurityContextHolder.getContext().getAuthentication().isAuthenticated();
    }

    public String validatePasswordResetToken(String token) {
        final Optional<PasswordResetToken> passToken = passwordResetTokenService.getByToken(token);

        return !passToken.isPresent() ? "invalidToken"
                : isTokenExpired(passToken.get()) ? "expired"
                : passToken.get().isUsed() ? "used"
                : null;
    }

    private boolean isTokenExpired(PasswordResetToken passToken) {
        return passToken.getExpiryDate().isBefore(LocalDateTime.now());
    }
}
