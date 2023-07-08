package ar.edu.itba.paw.webapp.auth;

import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.service.RestaurantService;
import ar.edu.itba.paw.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class SecurityManager {

    private final UserService userService;
    private final RestaurantService restaurantService;

    @Autowired
    public SecurityManager(UserService userService, RestaurantService restaurantService) {
        this.userService = userService;
        this.restaurantService = restaurantService;
    }

    public boolean isUserOfId(Authentication auth, long id) {
        if(!auth.isAuthenticated())
            return false;

        return userService.getByUsername(auth.getName()).filter(user -> user.getId() == id).isPresent();
    }

    public boolean isRestaurantOwnerWithoutRestaurant(Authentication auth) {
        if (!auth.isAuthenticated())
            return false;
        Optional<User> maybeUser = userService.getByUsername(auth.getName());
        if (!maybeUser.isPresent())
            return false;
        User user = maybeUser.get();
        if (!userService.isRestaurant(user.getId()))
            return false;
        return !restaurantService.getOfLoggedUser().isPresent();
    }

    public boolean isRestaurantOwnerOfId(Authentication auth, final long id) {
        return restaurantService.getById(id)
                .filter(r -> r.getUser().getUsername().equals(auth.getName()))
                .isPresent();
    }

}
