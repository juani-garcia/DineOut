package ar.edu.itba.paw.persistence;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.jdbc.JdbcTestUtils;

import static org.mockito.Mockito.when;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
// @Sql(scripts = {"classpath:sql/schema.sql"})
@ContextConfiguration(classes = TestConfig.class)
@Rollback
public class UserJdbcDaoTest {

    private static final String USER_TABLE = "account";

    private static final long ID = 1;
    private static final String USERNAME = "user@mail.com";
    private static final String FIRST_NAME = "John";
    private static final String LAST_NAME = "Doe";
    private static final String PASSWORD = "1234567890User";
    private static final String NEW_PASSWORD = "User1234567890";

    @Autowired
    private UserJdbcDao userJdbcDao;

    @Autowired
    private DataSource ds;

    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert jdbcInsert;

    @Before
    public void setUp() {
        userJdbcDao = new UserJdbcDao(ds);
        jdbcTemplate = new JdbcTemplate(ds);
        jdbcInsert = new SimpleJdbcInsert(ds).withTableName(USER_TABLE).usingGeneratedKeyColumns("id");
        JdbcTestUtils.deleteFromTables(jdbcTemplate, USER_TABLE);
    }

    @Test
    public void testRowMapper() throws SQLException {
        ResultSet resultSet = Mockito.mock(ResultSet.class);
        when(resultSet.getLong("id")).thenReturn(ID);
        when(resultSet.getString("username")).thenReturn(USERNAME);
        when(resultSet.getString("password")).thenReturn(PASSWORD);
        when(resultSet.getString("first_name")).thenReturn(FIRST_NAME);
        when(resultSet.getString("last_name")).thenReturn(LAST_NAME);

        User user = UserJdbcDao.ROW_MAPPER.mapRow(resultSet, 1);

        Assert.assertNotNull(user);
        Assert.assertEquals(ID, user.getId());
        Assert.assertEquals(USERNAME, user.getUsername());
        Assert.assertEquals(PASSWORD, user.getPassword());
        Assert.assertEquals(FIRST_NAME, user.getFirstName());
        Assert.assertEquals(LAST_NAME, user.getLastName());
    }

    @Test
    public void testFindByIdUserExists() {
        final Map<String, Object> userData = new HashMap<>();
        userData.put("username", USERNAME);
        userData.put("password", PASSWORD);
        userData.put("first_name", FIRST_NAME);
        userData.put("last_name", LAST_NAME);
        long id = jdbcInsert.executeAndReturnKey(userData).intValue();


        Optional<User> maybeUser = userJdbcDao.getById(id);

        assertTrue(maybeUser.isPresent());
        User user = maybeUser.get();
        Assert.assertEquals(id, user.getId());
        Assert.assertEquals(USERNAME, user.getUsername());
        Assert.assertEquals(PASSWORD, user.getPassword());
        Assert.assertEquals(FIRST_NAME, user.getFirstName());
        Assert.assertEquals(LAST_NAME, user.getLastName());
    }

    @Test
    public void testFindByIdUserDoesntExist() {

        Optional<User> user = userJdbcDao.getById(ID);

        assertFalse(user.isPresent());
    }



    @Test
    public void testFindByUsernameUserExists() {
        final Map<String, Object> userData = new HashMap<>();
        userData.put("username", USERNAME);
        userData.put("password", PASSWORD);
        userData.put("first_name", FIRST_NAME);
        userData.put("last_name", LAST_NAME);
        long id = jdbcInsert.executeAndReturnKey(userData).longValue();


        Optional<User> maybeUser = userJdbcDao.getByUsername(USERNAME);

        assertTrue(maybeUser.isPresent());
        User user = maybeUser.get();
        Assert.assertEquals(id, user.getId());
        Assert.assertEquals(USERNAME, user.getUsername());
        Assert.assertEquals(PASSWORD, user.getPassword());
        Assert.assertEquals(FIRST_NAME, user.getFirstName());
        Assert.assertEquals(LAST_NAME, user.getLastName());
    }

    @Test
    public void testFindByUsernameUserDoesntExist() {

        Optional<User> user = userJdbcDao.getByUsername(USERNAME);

        assertFalse(user.isPresent());
    }

    @Test
    public void testCreateUser() {
        // JdbcTestUtils.deleteFromTables(jdbcTemplate, USER_TABLE); //--> Already using rollback

        User user = userJdbcDao.create(USERNAME, PASSWORD, FIRST_NAME, LAST_NAME);

        assertNotNull(user);
        assertEquals(USERNAME, user.getUsername());
        assertEquals(PASSWORD, user.getPassword());
        assertEquals(FIRST_NAME, user.getFirstName());
        assertEquals(LAST_NAME, user.getLastName());
        assertEquals(1, JdbcTestUtils.countRowsInTable(jdbcTemplate, USER_TABLE));
    }

    @Test
    public void testCreateUserExistingUsername() {
        final Map<String, Object> userData = new HashMap<>();
        userData.put("username", USERNAME);
        userData.put("password", PASSWORD);
        userData.put("first_name", FIRST_NAME);
        userData.put("last_name", LAST_NAME);
        long id = jdbcInsert.executeAndReturnKey(userData).longValue();

        assertThrows(Exception.class, () -> userJdbcDao.create(USERNAME, PASSWORD, FIRST_NAME, LAST_NAME));
        assertEquals(1, JdbcTestUtils.countRowsInTable(jdbcTemplate, USER_TABLE));
    }

    @Test
    public void testUpdatePassword() {
        final Map<String, Object> userData = new HashMap<>();
        userData.put("username", USERNAME);
        userData.put("password", PASSWORD);
        userData.put("first_name", FIRST_NAME);
        userData.put("last_name", LAST_NAME);
        long id = jdbcInsert.executeAndReturnKey(userData).intValue();

        boolean success = userJdbcDao.updatePassword(NEW_PASSWORD, id);

        assertTrue(success);
        User user = jdbcTemplate.queryForObject("SELECT * FROM account WHERE id = ?",
                new Object[] {id},
                UserJdbcDao.ROW_MAPPER); //El ROW_MAPPER, si bien parte del jdbc a testear, ya esta testeado
        assertNotNull(user);
        assertEquals(USERNAME, user.getUsername());
        assertEquals(NEW_PASSWORD, user.getPassword());
        assertEquals(FIRST_NAME, user.getFirstName());
        assertEquals(LAST_NAME, user.getLastName());
        assertEquals(1, JdbcTestUtils.countRowsInTable(jdbcTemplate, USER_TABLE));
    }

    @Test
    public void testUpdatePasswordInexistingUser() {

        assertThrows(RuntimeException.class, () -> userJdbcDao.updatePassword(NEW_PASSWORD, ID));

    }

}
