package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.model.UserRole;

import java.util.List;
import java.util.Optional;

public interface UserService {

    Optional<User> getById(final long id);

    Optional<User> getByUsername(final String username);

    User create(final String username, final String password, final String firstName, final String lastName);
}