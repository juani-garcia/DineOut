package ar.edu.itba.paw.service;

import ar.edu.itba.paw.persistence.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SecurityServiceImpl implements SecurityService {

    @Autowired
    private UserService userService;

    public String getCurrentUsername() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    @Override
    public Optional<User> getCurrentUser() {
        // TODO: use Optional not null.
        String username = getCurrentUsername();

        if (username == null) return Optional.empty();

        return userService.getByUsername(getCurrentUsername());
    }

    @Override
    public boolean isLoggedIn() {
        return SecurityContextHolder.getContext().getAuthentication().isAuthenticated();
    }
}
