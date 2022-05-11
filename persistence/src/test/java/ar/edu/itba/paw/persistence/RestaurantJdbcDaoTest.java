package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.model.Zone;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.jdbc.JdbcTestUtils;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
// @Sql(scripts = {"classpath:sql/schema.sql"})
@ContextConfiguration(classes = TestConfig.class)
// @Rollback
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

    @Autowired
    private RestaurantJdbcDao restaurantDao;

    @Autowired
    private DataSource ds;

    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert jdbcInsert;

    @Before
    public void setUp() {
        restaurantDao = new RestaurantJdbcDao(ds);
        jdbcTemplate = new JdbcTemplate(ds);
        jdbcInsert = new SimpleJdbcInsert(ds).withTableName(RESTAURANT_TABLE).usingGeneratedKeyColumns("id");
        JdbcTestUtils.deleteFromTables(jdbcTemplate, RESTAURANT_TABLE);
    }

    @Test
    public void testRowMapper() throws SQLException {
        /* ResultSet rs = null; // TODO: Mock rs to return id 1 and username USERNAME

        Restaurant mapRow = RestaurantJdbcDao.ROW_MAPPER.mapRow(rs, 1);

        assertEquals(RESTAURANT_NAME, mapRow.getName()); */
    }

    @Test
    public void testCreateRestaurant() {
        // precondiciones
        // JdbcTestUtils.deleteFromTables(jdbcTemplate, USER_TABLE);

        // ejercitacion
        Restaurant restaurant = restaurantDao.create(USER_ID, NAME, IMAGE_ID, ADDRESS, MAIL, DETAIL, ZONE);

        // postcondiciones
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

//    @Test
//    public void testFindByIdRestaurantExists() {
//        JdbcTestUtils.deleteFromTables(jdbcTemplate, RESTAURANT_TABLE);
//        final Map<String, Object> restaurantData = new HashMap<>();
//        restaurantData.put("name", RESTAURANT_NAME);
//        restaurantData.put("address", RESTAURANT_ADDRESS);
//        restaurantData.put("mail", RESTAURANT_MAIL);
//        restaurantData.put("detail", RESTAURANT_DETAIL);
//
//        int id = jdbcInsert.executeAndReturnKey(restaurantData).intValue();
//
//        // ejercitacion

//        Optional<Restaurant> maybeRestaurant = restaurantDao.getById(id);
//
//        // postcondiciones
//        assertTrue(maybeRestaurant.isPresent());
//        assertEquals(RESTAURANT_NAME, maybeRestaurant.get().getName());
//    }

    @Test
    public void testFindByIdRestaurantDoesntExist() {
        // precondiciones
        // limpiar la db. Pero no puedo modifcar el restaurantDao!!! porque es el que testeo
        JdbcTestUtils.deleteFromTables(jdbcTemplate, "restaurant");

        // ejercitacion
        Optional<Restaurant> maybeRestaurant = restaurantDao.getById(1);

        // postcondiciones
        assertFalse(maybeRestaurant.isPresent());
    }

}
