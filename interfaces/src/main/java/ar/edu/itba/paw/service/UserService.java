package ar.edu.itba.paw.service;

import ar.edu.itba.paw.persistence.User;

import java.util.Optional;

public interface UserService {

    Optional<User> getById(final long id);

    Optional<User> getByUsername(final String username);

    User create(final String username, final String password, final String firstName, final String lastName);

    boolean isRestaurant(long userId);

    boolean isDiner(long userId);
}