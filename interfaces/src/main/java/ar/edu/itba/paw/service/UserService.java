package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.User;

import java.util.Optional;

public interface UserService {

    Optional<User> getById(final long id);

    Optional<User> getByUsername(final String username);

    User create(final String username, final String password, final String firstName, final String lastName, final Boolean isRestaurant, String contextPath);

    boolean isRestaurant(long userId);

    boolean isDiner(long userId);

    void createPasswordResetTokenForUser(User user, String contextPath);

    User getUserByPasswordResetToken(String token);

    void changePasswordByUserToken(String token, String newPassword);
}