package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.model.User;

import java.util.Optional;

// Data Access Object
public interface UserDao {

    Optional<User> getUserbyId(long id);

}
