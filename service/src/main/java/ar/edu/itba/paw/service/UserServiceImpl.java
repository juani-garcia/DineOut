package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.Restaurant;
import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.model.exceptions.UsernameNotAvailableException;
import ar.edu.itba.paw.persistence.RestaurantDao;
import ar.edu.itba.paw.persistence.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserDao userDao;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(final UserDao userDao, final PasswordEncoder passwordEncoder) {
        this.userDao = userDao;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Optional<User> getById(final long id) {
        return userDao.getById(id);
    }

    @Override
    public Optional<User> getByUsername(final String username) {
        return userDao.getByUsername(username);
    }

    @Override
    public User create(String username, String password) {
        if(getByUsername(username).isPresent()) {
            throw new UsernameNotAvailableException();
        }

        // TODO : send email validation mail
        // TODO : ...
        return userDao.create(username, passwordEncoder.encode(password));
    }


}
