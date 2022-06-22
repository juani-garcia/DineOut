package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.Role;
import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.model.UserRole;
import ar.edu.itba.paw.persistence.UserDao;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class UserServiceImplTest {

    private static final long ID = 1;
    private static final String USERNAME = "user@mail.com";
    private static final String PASSWORD = "1234567890User";
    private static final String FIRST_NAME = "John";
    private static final String LAST_NAME = "Doe";
    private static final Boolean IS_RESTAURANT = false;

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserDao userDao;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private UserRoleService userRoleService;
    @Mock
    private PasswordResetTokenService passwordResetTokenService;
    @Mock
    private EmailService emailService;

    @Test
    public void testCreateUser() {
        when(userDao.create(anyString(), any(), anyString(), anyString())).
                thenReturn(new User(USERNAME, PASSWORD, FIRST_NAME, LAST_NAME));
        when(userRoleService.getByRoleName(anyString())).
                thenReturn(Optional.of(new UserRole(1, Role.DINER.toString(), new ArrayList<>())));

        User user = null;
        try {
            user = userService.create(USERNAME, PASSWORD, FIRST_NAME, LAST_NAME, IS_RESTAURANT, null);
        } catch (Exception e) {
            System.out.println(e.getClass());
            Assert.fail("Unexpected error during operation create user: " + e.getMessage());
        }

        Assert.assertNotNull(user);
    }

    @Test
    public void testChangePasswordByUserToken() {
        when(userDao.create(anyString(), any(), anyString(), anyString())).
                thenReturn(new User(ID, USERNAME, PASSWORD, FIRST_NAME, LAST_NAME));
        when(userRoleService.getByRoleName(anyString())).
                thenReturn(Optional.of(new UserRole(1, Role.DINER.toString(), new ArrayList<>())));

        User user = null;
        try {
            user = userService.create(USERNAME, PASSWORD, FIRST_NAME, LAST_NAME, IS_RESTAURANT, null);
        } catch (Exception e) {
            System.out.println(e.getClass());
            Assert.fail("Unexpected error during operation create user: " + e.getMessage());
        }

        Assert.assertNotNull(user);
        Assert.assertEquals(Long.valueOf(ID), user.getId());
    }

}
