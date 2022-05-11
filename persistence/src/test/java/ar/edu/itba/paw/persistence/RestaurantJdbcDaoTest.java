package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.model.Restaurant;
import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.model.Zone;
import net.bytebuddy.build.Plugin;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.jdbc.JdbcTestUtils;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
// @Sql(scripts = {"classpath:sql/schema.sql"})
@ContextConfiguration(classes = TestConfig.class)
@Rollback
public class RestaurantJdbcDaoTest {

    private static final String RESTAURANT_TABLE = "restaurant";

    private static final long ID = 1;
    private static final long USER_ID = 1;
    private static final String NAME = "Atuel";
    private static final Long IMAGE_ID = null;
    private static final String ADDRESS = "Los Patos 2301";
    private static final String MAIL = "atuel@mail.com";
    private static final String DETAIL = "Detalle de Atuel";
    private static final Zone ZONE = Zone.ADROGUE;
    private static final Long FAV_COUNT = 0L;

    private static final String USER_USERNAME = "user@mail.com";
    private static final String USER_PASSWORD = "1234567890User";
    private static final String USER_FIRST_NAME = "John";
    private static final String USER_LAST_NAME = "Doe";
    private static final String USER_TABLE = "account";

    @Autowired
    private RestaurantJdbcDao restaurantDao;

    @Autowired
    private DataSource ds;

    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert jdbcInsert;
    private SimpleJdbcInsert userJdbcInsert;

    @Before
    public void setUp() {
        restaurantDao = new RestaurantJdbcDao(ds);
        jdbcTemplate = new JdbcTemplate(ds);
        jdbcInsert = new SimpleJdbcInsert(ds).withTableName(RESTAURANT_TABLE).usingGeneratedKeyColumns("id");
        JdbcTestUtils.deleteFromTables(jdbcTemplate, RESTAURANT_TABLE);

        userJdbcInsert = new SimpleJdbcInsert(ds).withTableName(USER_TABLE);
        JdbcTestUtils.deleteFromTables(jdbcTemplate, USER_TABLE);
        final Map<String, Object> userData = new HashMap<>();
        userData.put("id", USER_ID);
        userData.put("username", USER_USERNAME);
        userData.put("password", USER_PASSWORD);
        userData.put("first_name", USER_FIRST_NAME);
        userData.put("last_name", USER_LAST_NAME);
        userJdbcInsert.execute(userData);
    }

    @Test
    public void testRowMapper() throws SQLException {
        ResultSet resultSet = Mockito.mock(ResultSet.class);
        when(resultSet.getLong("id")).thenReturn(ID);
        when(resultSet.getLong("user_id")).thenReturn(USER_ID);
        when(resultSet.getString("name")).thenReturn(NAME);
        when(resultSet.getLong("image_id")).thenReturn(IMAGE_ID == null ? 0L : IMAGE_ID);
        when(resultSet.getString("address")).thenReturn(ADDRESS);
        when(resultSet.getString("mail")).thenReturn(MAIL);
        when(resultSet.getString("detail")).thenReturn(DETAIL);
        when(resultSet.getLong("zone_id")).thenReturn(ZONE.getId());
        when(resultSet.getLong("fav_count")).thenReturn(FAV_COUNT);

        Restaurant restaurant = RestaurantJdbcDao.ROW_MAPPER.mapRow(resultSet, 1);

        Assert.assertNotNull(restaurant);
        Assert.assertEquals(ID, restaurant.getId());
        Assert.assertEquals(USER_ID, restaurant.getUserID());
        Assert.assertEquals(NAME, restaurant.getName());
        Assert.assertEquals(IMAGE_ID, restaurant.getImageId());
        Assert.assertEquals(ADDRESS, restaurant.getAddress());
        Assert.assertEquals(MAIL, restaurant.getMail());
        Assert.assertEquals(DETAIL, restaurant.getDetail());
        Assert.assertEquals(ZONE, restaurant.getZone());
        Assert.assertEquals(FAV_COUNT, restaurant.getFavCount());
    }

    @Test
    public void testCreateRestaurant() {

        Restaurant restaurant = restaurantDao.create(USER_ID, NAME, IMAGE_ID, ADDRESS, MAIL, DETAIL, ZONE);

        assertNotNull(restaurant);
        assertEquals(USER_ID, restaurant.getUserID());
        assertEquals(NAME, restaurant.getName());
        assertEquals(IMAGE_ID, restaurant.getImageId());
        assertEquals(ADDRESS, restaurant.getAddress());
        assertEquals(MAIL, restaurant.getMail());
        assertEquals(DETAIL, restaurant.getDetail());
        assertEquals(ZONE, restaurant.getZone());
        assertEquals(1, JdbcTestUtils.countRowsInTable(jdbcTemplate, RESTAURANT_TABLE));
    }

    @Test
    public void testCreateRestaurantAlreadyInUseMail() {
        final Map<String, Object> restaurantData = new HashMap<>();
        restaurantData.put("name", NAME);
        restaurantData.put("user_id", USER_ID);
        restaurantData.put("address", ADDRESS);
        restaurantData.put("mail", MAIL);
        restaurantData.put("detail", DETAIL);
        restaurantData.put("zone_id", ZONE.getId());
        restaurantData.put("image_id", IMAGE_ID);
        long id = jdbcInsert.executeAndReturnKey(restaurantData).longValue();

        assertThrows(Exception.class, () -> restaurantDao.create(USER_ID, NAME, IMAGE_ID, ADDRESS, MAIL, DETAIL, ZONE));
    }

    @Test
    public void testFindByIdRestaurantExists() {
        final Map<String, Object> restaurantData = new HashMap<>();
        restaurantData.put("name", NAME);
        restaurantData.put("user_id", USER_ID);
        restaurantData.put("address", ADDRESS);
        restaurantData.put("mail", MAIL);
        restaurantData.put("detail", DETAIL);
        restaurantData.put("zone_id", ZONE.getId());
        restaurantData.put("image_id", IMAGE_ID);
        long id = jdbcInsert.executeAndReturnKey(restaurantData).longValue();

        Optional<Restaurant> maybeRestaurant = restaurantDao.getById(id);

        assertTrue(maybeRestaurant.isPresent());
        Restaurant restaurant = maybeRestaurant.get();
        assertEquals(NAME, restaurant.getName());
        assertEquals(USER_ID, restaurant.getUserID());
        assertEquals(ADDRESS, restaurant.getAddress());
        assertEquals(MAIL, restaurant.getMail());
        assertEquals(DETAIL, restaurant.getDetail());
        assertEquals(ZONE, restaurant.getZone());
        assertEquals(IMAGE_ID, restaurant.getImageId());
    }

    @Test
    public void testFindByIdRestaurantDoesntExist() {

        Optional<Restaurant> maybeRestaurant = restaurantDao.getById(ID);

        assertFalse(maybeRestaurant.isPresent());
    }

    @Test
    public void testFindByMailExists() {
        final Map<String, Object> restaurantData = new HashMap<>();
        restaurantData.put("name", NAME);
        restaurantData.put("user_id", USER_ID);
        restaurantData.put("address", ADDRESS);
        restaurantData.put("mail", MAIL);
        restaurantData.put("detail", DETAIL);
        restaurantData.put("zone_id", ZONE.getId());
        restaurantData.put("image_id", IMAGE_ID);
        long id = jdbcInsert.executeAndReturnKey(restaurantData).longValue();

        Optional<Restaurant> maybeRestaurant = restaurantDao.getByMail(MAIL);

        assertTrue(maybeRestaurant.isPresent());
        Restaurant restaurant = maybeRestaurant.get();
        assertEquals(NAME, restaurant.getName());
        assertEquals(USER_ID, restaurant.getUserID());
        assertEquals(ADDRESS, restaurant.getAddress());
        assertEquals(MAIL, restaurant.getMail());
        assertEquals(DETAIL, restaurant.getDetail());
        assertEquals(ZONE, restaurant.getZone());
        assertEquals(IMAGE_ID, restaurant.getImageId());
    }

    @Test
    public void testFindByMailRestaurantDoesntExist() {

        Optional<Restaurant> maybeRestaurant = restaurantDao.getByMail(MAIL);

        assertFalse(maybeRestaurant.isPresent());
    }

    @Test
    public void testFindByUserIdExists() {
        final Map<String, Object> restaurantData = new HashMap<>();
        restaurantData.put("name", NAME);
        restaurantData.put("user_id", USER_ID);
        restaurantData.put("address", ADDRESS);
        restaurantData.put("mail", MAIL);
        restaurantData.put("detail", DETAIL);
        restaurantData.put("zone_id", ZONE.getId());
        restaurantData.put("image_id", IMAGE_ID);
        long id = jdbcInsert.executeAndReturnKey(restaurantData).longValue();

        Optional<Restaurant> maybeRestaurant = restaurantDao.getByUserId(USER_ID);

        assertTrue(maybeRestaurant.isPresent());
        Restaurant restaurant = maybeRestaurant.get();
        assertEquals(NAME, restaurant.getName());
        assertEquals(USER_ID, restaurant.getUserID());
        assertEquals(ADDRESS, restaurant.getAddress());
        assertEquals(MAIL, restaurant.getMail());
        assertEquals(DETAIL, restaurant.getDetail());
        assertEquals(ZONE, restaurant.getZone());
        assertEquals(IMAGE_ID, restaurant.getImageId());
    }

    @Test
    public void testFindByUserIdRestaurantDoesntExist() {

        Optional<Restaurant> maybeRestaurant = restaurantDao.getByUserId(USER_ID);

        assertFalse(maybeRestaurant.isPresent());
    }

    @Test
    public void testUpdateRestaurant() {
        final Map<String, Object> restaurantData = new HashMap<>();
        restaurantData.put("name", NAME);
        restaurantData.put("user_id", USER_ID);
        restaurantData.put("address", ADDRESS);
        restaurantData.put("mail", MAIL);
        restaurantData.put("detail", DETAIL);
        restaurantData.put("zone_id", ZONE.getId());
        restaurantData.put("image_id", IMAGE_ID);
        long id = jdbcInsert.executeAndReturnKey(restaurantData).longValue();

        Optional<Restaurant> maybeRestaurant = restaurantDao.getByUserId(USER_ID);

        assertTrue(maybeRestaurant.isPresent());
        Restaurant restaurant = maybeRestaurant.get();
        assertEquals(NAME, restaurant.getName());
        assertEquals(USER_ID, restaurant.getUserID());
        assertEquals(ADDRESS, restaurant.getAddress());
        assertEquals(MAIL, restaurant.getMail());
        assertEquals(DETAIL, restaurant.getDetail());
        assertEquals(ZONE, restaurant.getZone());
        assertEquals(IMAGE_ID, restaurant.getImageId());
    }

}
