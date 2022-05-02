package ar.edu.itba.paw.service;

import ar.edu.itba.paw.persistence.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class SecurityServiceImpl implements SecurityService {

    @Autowired
    private UserService userService;

    private User cachedUser;

    public String getCurrentUsername() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    @Override
    public User getCurrentUser() {
        String username = getCurrentUsername();

        if (username == null) return null;

        if (cachedUser == null || !cachedUser.getUsername().equals(username)) {
            cachedUser = userService.getByUsername(username).orElse(null);
        }
        return cachedUser;
    }
}
