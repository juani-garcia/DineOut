package ar.edu.itba.paw.webapp.auth;

import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.service.ReservationService;
import ar.edu.itba.paw.service.RestaurantReviewService;
import ar.edu.itba.paw.service.RestaurantService;
import ar.edu.itba.paw.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

@Component
public class SecurityManager {

    private final UserService userService;
    private final RestaurantService restaurantService;
    private final RestaurantReviewService restaurantReviewService;
    private final ReservationService reservationService;
    private static final Logger LOGGER = LoggerFactory.getLogger(SecurityManager.class);

    @Autowired
    public SecurityManager(UserService userService, RestaurantService restaurantService, ReservationService reservationService, RestaurantReviewService restaurantReviewService) {
        this.userService = userService;
        this.restaurantService = restaurantService;
        this.reservationService = reservationService;
        this.restaurantReviewService = restaurantReviewService;
    }

    public boolean isUserOfId(Authentication auth, Long id) {
        LOGGER.debug("Requested user id: {}", id);
        if (!auth.isAuthenticated() || id == null)
            return false;
        if (auth instanceof AnonymousAuthenticationToken) {
            LOGGER.debug("User is not authenticated");
            return false;
        }
        LOGGER.debug("Authenticated user: {}", auth.getName());
        return userService.getByUsername(auth.getName()).filter(user -> Objects.equals(user.getId(), id)).isPresent();
    }

    public boolean isRestaurantOwnerWithoutRestaurant(Authentication auth) {
        if (!auth.isAuthenticated())
            return false;
        Optional<User> maybeUser = userService.getByUsername(auth.getName());
        if (!maybeUser.isPresent())
            return false;
        User user = maybeUser.get();
        if (!userService.isRestaurant(user.getId())) // TODO: Check if we can use RoleService with @Secured (https://bitbucket.org/itba/paw-2022a-10/pull-requests/122#comment-410600704)
            return false;
        return !restaurantService.getOfLoggedUser().isPresent();
    }

    public boolean isRestaurantOwnerOfId(Authentication auth, final Long id) {
        if (id == null)
            return false;
        return restaurantService.getById(id)
                .filter(r -> r.getUser().getUsername().equals(auth.getName())) // TODO: Check if NPE is possible (https://bitbucket.org/itba/paw-2022a-10/pull-requests/122#comment-410599200)
                .isPresent();
    }

    public boolean isReviewOwner(Authentication auth, final long reviewId) {
        return restaurantReviewService.getById(reviewId)
                .filter(rr -> rr.getUser().getUsername().equals(auth.getName()))
                .isPresent();
    }

    public boolean isReservationRestaurant(Authentication auth, final long reservationId) {
        return reservationService.getById(reservationId)
                .filter(r -> r.getOwner().getUsername().equals(auth.getName()))
                .isPresent();
    }

    public boolean isReservationOwner(Authentication auth, final long reservationId) {
        return reservationService.getById(reservationId)
                .filter(r -> r.getOwner().getUsername().equals(auth.getName()))
                .isPresent();
    }

}
