package ar.edu.itba.paw.persistence;

import java.util.Optional;

public interface UserDao {

    Optional<User> getById(final long id);

    Optional<User> getByUsername(final String username);

    User create(final String username, final String password, final String firstName, final String lastName);
}
