package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.model.PagedQuery;
import ar.edu.itba.paw.model.User;

import java.util.Locale;
import java.util.Optional;

public interface UserDao {

    PagedQuery<User> getUsers(final int page);

    Optional<User> getById(final long id);

    Optional<User> getByUsername(final String username);

    User create(final String username, final String password, final String firstName, final String lastName, Locale locale);
}
