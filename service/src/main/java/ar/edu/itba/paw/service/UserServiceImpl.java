package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.model.UserToRole;
import ar.edu.itba.paw.persistence.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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
    public User create(String username, String password, final String firstName, final String lastName) {
        // TODO : validate username / password
        // TODO : send email validation mail
        // TODO : ...
        return userDao.create(username, passwordEncoder.encode(password), firstName, lastName);
    }


}
