package ar.edu.itba.paw.service;

import ar.edu.itba.paw.persistence.User;
import ar.edu.itba.paw.persistence.UserDao;
import ar.edu.itba.paw.persistence.UserRole;
import ar.edu.itba.paw.persistence.UserToRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserDao userDao;
    private final PasswordEncoder passwordEncoder;
    private final UserRoleService userRoleService;
    private final UserToRoleService userToRoleService;
    private final EmailService emailService;

    @Autowired
    public UserServiceImpl(final UserDao userDao, final PasswordEncoder passwordEncoder,
                           UserRoleService userRoleService, UserToRoleService userToRoleService,
                           EmailService emailService) {
        this.userDao = userDao;
        this.passwordEncoder = passwordEncoder;
        this.userRoleService = userRoleService;
        this.userToRoleService = userToRoleService;
        this.emailService = emailService;
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
    public User create(String username, String password, final String firstName, final String lastName, final Boolean isRestaurant) {
        User user = userDao.create(username, passwordEncoder.encode(password), firstName, lastName);
        if (user == null) return null;

        Optional<UserRole> userRole;
        String role = isRestaurant ? "RESTAURANT" : "DINER";
        userRole = userRoleService.getByRoleName(role);
        if (!userRole.isPresent()) throw new IllegalStateException("El rol " + role + " no esta presente en la bbdd");
        UserToRole userToRole = userToRoleService.create(user.getId(), userRole.get().getId());

        // TODO: rollback if not able to create userToRole

        emailService.sendAccountCreationMail(user.getUsername(), user.getFirstName());

        return user;
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
