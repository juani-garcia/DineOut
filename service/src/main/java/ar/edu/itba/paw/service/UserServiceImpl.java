package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.PasswordResetToken;
import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.model.UserRole;
import ar.edu.itba.paw.model.exceptions.NotFoundException;
import ar.edu.itba.paw.persistence.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    private final UserDao userDao;
    private final PasswordEncoder passwordEncoder;
    private final UserRoleService userRoleService;
    private final PasswordResetTokenService passwordResetTokenService;
    private final EmailService emailService;

    @Autowired
    public UserServiceImpl(final UserDao userDao, final PasswordEncoder passwordEncoder,
                           UserRoleService userRoleService,
                           PasswordResetTokenService passwordResetTokenService, EmailService emailService) {


        this.userDao = userDao;
        this.passwordEncoder = passwordEncoder;
        this.userRoleService = userRoleService;
        this.passwordResetTokenService = passwordResetTokenService;
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

    @Transactional
    @Override
    public User create(String username, String password, final String firstName, final String lastName, final Boolean isRestaurant) {
        User user = userDao.create(username, passwordEncoder.encode(password), firstName, lastName);
        if (user == null) return null;

        String role = isRestaurant ? "RESTAURANT" : "DINER";
        UserRole userRole = userRoleService.getByRoleName(role)
                .orElseThrow( () -> new IllegalStateException("El rol " + role + " no esta presente en la bbdd"));
        user.addRole(userRole);

        LocaleContextHolder.setLocale(LocaleContextHolder.getLocale(), true);
        emailService.sendAccountCreationMail(user.getUsername(), user.getFirstName());

        return user;
    }

    @Override
    public boolean isRestaurant(long userId) {
        return isRole(userId, "RESTAURANT");
    }

    @Override
    public boolean isDiner(long userId) {
        return isRole(userId, "DINER");
    }

    private boolean isRole(long userId, String role) {
        User user = userDao.getById(userId).orElseThrow(NotFoundException::new);
        UserRole userRole = userRoleService.getByRoleName(role).orElseThrow(NotFoundException::new);
        return user.getRoles().contains(userRole);
    }

    @Override
    public void createPasswordResetTokenForUser(User user, String contextPath) {
        if (passwordResetTokenService.hasValidToken(user.getId())) return;
        PasswordResetToken passwordResetToken = passwordResetTokenService.create(UUID.randomUUID().toString(), user, LocalDateTime.now());
        LocaleContextHolder.setLocale(LocaleContextHolder.getLocale(), true);
        emailService.sendChangePassword(user.getUsername(), user.getFirstName(), contextPath + "/change_password?token=" + passwordResetToken.getToken());

    }

    @Override
    public User getUserByPasswordResetToken(String token) {
        PasswordResetToken passwordResetToken = passwordResetTokenService.getByToken(token).orElseThrow(IllegalStateException::new);
        return passwordResetToken.getUser();
    }

    @Override
    @Transactional
    public void changePasswordByUserToken(String token, String newPassword) {
        User user = getUserByPasswordResetToken(token);
        user.setPassword(passwordEncoder.encode(newPassword));  // TODO: Check if not changed?
        passwordResetTokenService.setUsed(token);
    }

}
