package ar.edu.itba.paw.webapp.auth;

import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class SecurityManager {

    private final UserService userService;

    @Autowired
    public SecurityManager(UserService userService) {
        this.userService = userService;
    }

    public boolean validateAuthById(Authentication auth, long id) {
        if(!auth.isAuthenticated())
            return false;

        return userService.getByUsername(auth.getName()).filter(user -> user.getId() == id).isPresent();

    }

}
