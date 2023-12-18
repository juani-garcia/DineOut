package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.*;
import ar.edu.itba.paw.model.exceptions.InvalidPageException;
import ar.edu.itba.paw.model.exceptions.InvalidPasswordRecoveryTokenException;
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
    public PagedQuery<User> getUsers(final int page) {
        if (page <= 0)
            throw new InvalidPageException();

        return userDao.getUsers(page);
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
    public User create(String username, String password, final String firstName, final String lastName, final Boolean isRestaurant, String contextPath) {
        User user = userDao.create(username, passwordEncoder.encode(password), firstName, lastName, LocaleContextHolder.getLocale());
        if (user == null) return null;

        String role = isRestaurant ? Role.RESTAURANT.getRoleName() : Role.DINER.getRoleName();
        UserRole userRole = userRoleService.getByRoleName(role)
                .orElseThrow( () -> new IllegalStateException("El rol " + role + " no esta presente en la bbdd"));
        user.addRole(userRole);

        emailService.sendAccountCreationMail(user.getUsername(), user.getFirstName(), contextPath, user.getLocale());

        return user;
    }

    @Override
    @Transactional
    public Optional<User> edit(User user, String firstName, String lastName, String contextPath) {
        if (user.getFirstName().equals(firstName) && user.getLastName().equals(lastName)) return Optional.empty();

        user.setFirstName(firstName);
        user.setLastName(lastName);

        emailService.sendAccountModification(user.getUsername(), user.getFirstName(), contextPath, user.getLocale());

        return Optional.of(user);
    }

    @Override
    public boolean isRestaurant(long userId) {
        return isRole(userId, Role.RESTAURANT.getRoleName());
    }

    @Override
    public boolean isDiner(long userId) {
        return isRole(userId, Role.DINER.getRoleName());
    }

    private boolean isRole(long userId, String role) {
        User user = userDao.getById(userId).orElseThrow(NotFoundException::new);
        UserRole userRole = userRoleService.getByRoleName(role).orElseThrow(NotFoundException::new);
        return user.getRoles().contains(userRole);
    }

    @Transactional
    @Override
    public void createPasswordResetTokenByUsername(String username, String contextPath) {
        final User user = getByUsername(username).orElseThrow(NotFoundException::new);

        if (passwordResetTokenService.hasValidToken(user.getId())) return;
        PasswordResetToken passwordResetToken = passwordResetTokenService.create(UUID.randomUUID().toString(), user, LocalDateTime.now());
        emailService.sendChangePassword(
                user.getUsername(), user.getFirstName(),
                contextPath + "/password-recovery?token=" + passwordResetToken.getToken() + "&userId=" + user.getId().toString(),
                contextPath,
                user.getLocale()
        );

    }

    @Override
    public User getUserByPasswordResetToken(String token) {
        PasswordResetToken resetToken = passwordResetTokenService.getByToken(token).
                orElseThrow(InvalidPasswordRecoveryTokenException::new);

        if (resetToken.isUsed()) {
            throw new InvalidPasswordRecoveryTokenException();
        }

        return resetToken.getUser();
    }

    @Override
    @Transactional
    public void changePasswordByUserToken(String token, String newPassword) {
        User user = getUserByPasswordResetToken(token);
        user.setPassword(passwordEncoder.encode(newPassword));
        passwordResetTokenService.setUsed(token);
    }

}
