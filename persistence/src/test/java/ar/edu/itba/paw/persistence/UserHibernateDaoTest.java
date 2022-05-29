//package ar.edu.itba.paw.persistence;
//
//import ar.edu.itba.paw.model.User;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//import org.springframework.transaction.annotation.Transactional;
//
//import javax.persistence.EntityManager;
//import javax.persistence.PersistenceContext;
//import java.util.Optional;
//
//import static org.junit.Assert.*;
//
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(classes = TestConfig.class)
//@Transactional
//public class UserHibernateDaoTest {
//
//    @Autowired
//    private UserHibernateDao userHibernateDao;
//
//    @PersistenceContext
//    private EntityManager em;
//
//    @Before
//    public void setUp() {
//
//    }
//
//    @Test
//    public void testFindByIdUserExists() {
//        User user = TestHelper.createUser(em, TestValues.USER_USERNAME, TestValues.USER_PASSWORD, TestValues.USER_FIRST_NAME, TestValues.USER_LAST_NAME);
//
//        Optional<User> maybeUser = userHibernateDao.getById(user.getId());
//
//        assertTrue(maybeUser.isPresent());
//        user = maybeUser.get();
//        assertEquals(TestValues.USER_USERNAME, user.getUsername());
//        assertEquals(TestValues.USER_PASSWORD, user.getPassword());
//        assertEquals(TestValues.USER_FIRST_NAME, user.getFirstName());
//        assertEquals(TestValues.USER_LAST_NAME, user.getLastName());
//    }
//
//    @Test
//    public void testFindByIdUserDoesntExist() {
//        Optional<User> user = userHibernateDao.getById(TestValues.USER_ID);
//
//        assertFalse(user.isPresent());
//    }
//
//    @Test
//    public void testFindByUsernameUserExists() {
//        TestHelper.createUser(em, TestValues.USER_USERNAME, TestValues.USER_PASSWORD, TestValues.USER_FIRST_NAME, TestValues.USER_LAST_NAME);
//
//        Optional<User> maybeOptionalUser = userHibernateDao.getByUsername(TestValues.USER_USERNAME);
//
//        assertTrue(maybeOptionalUser.isPresent());
//        User user = maybeOptionalUser.get();
//        assertEquals(TestValues.USER_USERNAME, user.getUsername());
//        assertEquals(TestValues.USER_PASSWORD, user.getPassword());
//        assertEquals(TestValues.USER_FIRST_NAME, user.getFirstName());
//        assertEquals(TestValues.USER_LAST_NAME, user.getLastName());
//    }
//
//    @Test
//    public void testFindByUsernameUserDoesntExist() {
//        Optional<User> user = userHibernateDao.getByUsername(TestValues.USER_USERNAME);
//        assertFalse(user.isPresent());
//    }
//
//
//    @Test
//    public void testCreateUser() {
//        assertEquals(0, TestHelper.getRows(em, TestValues.USER_TABLE));
//
//        User user = userHibernateDao.create(TestValues.USER_USERNAME, TestValues.USER_PASSWORD, TestValues.USER_FIRST_NAME, TestValues.USER_LAST_NAME);
//
//        assertNotNull(user);
//        assertTrue(user.getId() > 0L);
//        assertEquals(TestValues.USER_USERNAME, user.getUsername());
//        assertEquals(TestValues.USER_PASSWORD, user.getPassword());
//        assertEquals(TestValues.USER_FIRST_NAME, user.getFirstName());
//        assertEquals(TestValues.USER_LAST_NAME, user.getLastName());
//        assertEquals(1, TestHelper.getRows(em, TestValues.USER_TABLE));
//    }
//
////    @Test
////    public void testCreateUserExistingUsername() {
////        TestHelper.createUser(em, TestValues.USER_USERNAME, TestValues.USER_PASSWORD, TestValues.USER_FIRST_NAME, TestValues.USER_LAST_NAME);
////
////        assertThrows(Exception.class, () -> userHibernateDao.create(TestValues.USER_USERNAME, TestValues.USER_PASSWORD, TestValues.USER_FIRST_NAME, TestValues.USER_LAST_NAME));
////        assertEquals(1, TestHelper.getRows(em, TestValues.USER_TABLE));
////    }
//
//}
