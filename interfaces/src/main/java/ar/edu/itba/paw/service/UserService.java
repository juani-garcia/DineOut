package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.PagedQuery;
import ar.edu.itba.paw.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    PagedQuery<User> getUsers(final int page);

    Optional<User> getById(final long id);

    Optional<User> getByUsername(final String username);

    User create(final String username, final String password, final String firstName, final String lastName, final Boolean isRestaurant, String contextPath);
    Optional<User> edit(final User user, final String firstName, final String lastName, String contextPath);
    void editPassword(final User user, final String password);

    boolean isRestaurant(long userId);

    boolean isDiner(long userId);

    void createPasswordResetTokenByUsername(String username, String contextPath);

    Optional<User> getUserByPasswordResetToken(String token);
}
