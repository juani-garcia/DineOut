package ar.edu.itba.paw.service;

import ar.edu.itba.paw.persistence.User;
import ar.edu.itba.paw.model.exceptions.DuplicatedUsernameException;
import ar.edu.itba.paw.persistence.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserDao userDao;
    private final PasswordEncoder passwordEncoder;
    private final UserRoleService userRoleService;

    @Autowired
    public UserServiceImpl(final UserDao userDao, final PasswordEncoder passwordEncoder, UserRoleService userRoleService) {
        this.userDao = userDao;
        this.passwordEncoder = passwordEncoder;
        this.userRoleService = userRoleService;
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
        if(getByUsername(username).isPresent()) {
            DuplicatedUsernameException ex = new DuplicatedUsernameException();
            ex.setUsername(username);
            ex.setPassword(password);
            ex.setLastName(lastName);
            ex.setFirstName(firstName);
            throw ex;
        }

        // TODO : send email validation mail
        return userDao.create(username, passwordEncoder.encode(password), firstName, lastName);
    }

    @Override
    public boolean isRestaurant(long userId) {
        return userRoleService.hasRoleByUserId(userId, "RESTAURANT");
    }

    @Override
    public boolean isDiner(long userId) {
        return userRoleService.hasRoleByUserId(userId, "DINER");
    }


}
