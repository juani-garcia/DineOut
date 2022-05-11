package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.model.UserRole;
import ar.edu.itba.paw.model.UserToRole;
import ar.edu.itba.paw.persistence.*;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.mail.MessagingException;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doThrow;
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
    private UserToRoleService userToRoleService;
    @Mock
    private PasswordResetTokenService passwordResetTokenService;
    @Mock
    private EmailService emailService;

    @Test
    public void testCreateUser() {
        when(userDao.create(anyString(), any(), anyString(), anyString())).
                thenReturn(new User(ID, USERNAME, PASSWORD, FIRST_NAME, LAST_NAME));
        when(userRoleService.getByRoleName(anyString())).
                thenReturn(Optional.of(new UserRole(1, "DINER")));
        when(userToRoleService.create(anyLong(), anyLong())).
                thenReturn(new UserToRole(1, ID, 1));

        User user = null;
        try {
            user = userService.create(USERNAME, PASSWORD, FIRST_NAME, LAST_NAME, IS_RESTAURANT);
        } catch (Exception e) {
            System.out.println(e.getClass());
            Assert.fail("Unexpected error during operation create user: " + e.getMessage());
        }

        Assert.assertNotNull(user);
        Assert.assertEquals(ID, user.getId());
    }

}
