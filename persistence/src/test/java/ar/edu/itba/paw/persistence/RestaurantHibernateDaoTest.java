//package ar.edu.itba.paw.persistence;
//
//import org.junit.Before;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.test.annotation.Rollback;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//import org.springframework.transaction.annotation.Transactional;
//
//import javax.persistence.EntityManager;
//import javax.persistence.PersistenceContext;
//
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(classes = TestConfig.class)
//@Transactional
//public class RestaurantHibernateDaoTest {
//
//
//    @Autowired
//    private RestaurantJdbcDao restaurantDao;
//    @Autowired
//    private UserHibernateDao userHibernateDao;
//
//    @PersistenceContext
//    private EntityManager em;
//
//    @Before
//    public void setUp() {
//        // Create user
//        userHibernateDao.create(TestValues.USER_USERNAME, TestValues.USER_PASSWORD, TestValues.USER_FIRST_NAME, TestValues.USER_LAST_NAME);
//
//
//    }
//
//    @Before
//    public void setUp() {
//
//        userJdbcInsert = new SimpleJdbcInsert(ds).withTableName(TestValues.USER_TABLE);
//        JdbcTestUtils.deleteFromTables(jdbcTemplate, TestValues.USER_TABLE);
//        final Map<String, Object> userData = new HashMap<>();
//        userData.put("id", TestValues.USER_ID);
//        userData.put("username", TestValues.USER_USERNAME);
//        userData.put("password", TestValues.USER_PASSWORD);
//        userData.put("first_name", TestValues.USER_FIRST_NAME);
//        userData.put("last_name", TestValues.USER_LAST_NAME);
//        userJdbcInsert.execute(userData);
//    }
//
//    @Test
//    public void testRowMapper() throws SQLException {
//        ResultSet resultSet = Mockito.mock(ResultSet.class);
//        when(resultSet.getLong("id")).thenReturn(TestValues.ID);
//        when(resultSet.getLong("user_id")).thenReturn(TestValues.USER_ID);
//        when(resultSet.getString("name")).thenReturn(TestValues.NAME);
//        when(resultSet.getLong("image_id")).thenReturn(TestValues.IMAGE_ID == null ? 0L : TestValues.IMAGE_ID);
//        when(resultSet.getString("address")).thenReturn(TestValues.ADDRESS);
//        when(resultSet.getString("mail")).thenReturn(TestValues.MAIL);
//        when(resultSet.getString("detail")).thenReturn(TestValues.DETAIL);
//        when(resultSet.getLong("zone_id")).thenReturn(TestValues.ZONE.getId());
//        when(resultSet.getLong("fav_count")).thenReturn(TestValues.FAV_COUNT);
//
//        Restaurant restaurant = RestaurantJdbcDao.ROW_MAPPER.mapRow(resultSet, 1);
//
//        Assert.assertNotNull(restaurant);
//        Assert.assertEquals(TestValues.ID, restaurant.getId());
//        Assert.assertEquals(TestValues.USER_ID, restaurant.getUserID());
//        Assert.assertEquals(TestValues.NAME, restaurant.getName());
//        Assert.assertEquals(TestValues.IMAGE_ID, restaurant.getImageId());
//        Assert.assertEquals(TestValues.ADDRESS, restaurant.getAddress());
//        Assert.assertEquals(TestValues.MAIL, restaurant.getMail());
//        Assert.assertEquals(TestValues.DETAIL, restaurant.getDetail());
//        Assert.assertEquals(TestValues.ZONE, restaurant.getZone());
//        Assert.assertEquals(TestValues.FAV_COUNT, restaurant.getFavCount());
//    }
//
//    @Test
//    public void testCreateRestaurant() {
//
//        Restaurant restaurant = restaurantDao.create(TestValues.USER_ID, TestValues.NAME, TestValues.IMAGE_ID, TestValues.ADDRESS, TestValues.MAIL, TestValues.DETAIL, TestValues.ZONE);
//
//        assertNotNull(restaurant);
//        assertEquals(TestValues.USER_ID, restaurant.getUserID());
//        assertEquals(TestValues.NAME, restaurant.getName());
//        assertEquals(TestValues.IMAGE_ID, restaurant.getImageId());
//        assertEquals(TestValues.ADDRESS, restaurant.getAddress());
//        assertEquals(TestValues.MAIL, restaurant.getMail());
//        assertEquals(TestValues.DETAIL, restaurant.getDetail());
//        assertEquals(TestValues.ZONE, restaurant.getZone());
//        assertEquals(1, JdbcTestUtils.countRowsInTable(jdbcTemplate, TestValues.RESTAURANT_TABLE));
//    }
//
//    @Test
//    public void testCreateRestaurantAlreadyInUseMail() {
//        final Map<String, Object> restaurantData = new HashMap<>();
//        restaurantData.put("name", TestValues.NAME);
//        restaurantData.put("user_id", TestValues.USER_ID);
//        restaurantData.put("address", TestValues.ADDRESS);
//        restaurantData.put("mail", MAIL);
//        restaurantData.put("detail", DETAIL);
//        restaurantData.put("zone_id", ZONE.getId());
//        restaurantData.put("image_id", IMAGE_ID);
//        long id = jdbcInsert.executeAndReturnKey(restaurantData).longValue();
//
//        assertThrows(Exception.class, () -> restaurantDao.create(USER_ID, NAME, IMAGE_ID, ADDRESS, MAIL, DETAIL, ZONE));
//    }
//
//    @Test
//    public void testFindByIdRestaurantExists() {
//        final Map<String, Object> restaurantData = new HashMap<>();
//        restaurantData.put("name", NAME);
//        restaurantData.put("user_id", USER_ID);
//        restaurantData.put("address", ADDRESS);
//        restaurantData.put("mail", MAIL);
//        restaurantData.put("detail", DETAIL);
//        restaurantData.put("zone_id", ZONE.getId());
//        restaurantData.put("image_id", IMAGE_ID);
//        long id = jdbcInsert.executeAndReturnKey(restaurantData).longValue();
//
//        Optional<Restaurant> maybeRestaurant = restaurantDao.getById(id);
//
//        assertTrue(maybeRestaurant.isPresent());
//        Restaurant restaurant = maybeRestaurant.get();
//        assertEquals(NAME, restaurant.getName());
//        assertEquals(USER_ID, restaurant.getUserID());
//        assertEquals(ADDRESS, restaurant.getAddress());
//        assertEquals(MAIL, restaurant.getMail());
//        assertEquals(DETAIL, restaurant.getDetail());
//        assertEquals(ZONE, restaurant.getZone());
//        assertEquals(IMAGE_ID, restaurant.getImageId());
//    }
//
//    @Test
//    public void testFindByIdRestaurantDoesntExist() {
//
//        Optional<Restaurant> maybeRestaurant = restaurantDao.getById(ID);
//
//        assertFalse(maybeRestaurant.isPresent());
//    }
//
//    @Test
//    public void testFindByMailExists() {
//        final Map<String, Object> restaurantData = new HashMap<>();
//        restaurantData.put("name", NAME);
//        restaurantData.put("user_id", USER_ID);
//        restaurantData.put("address", ADDRESS);
//        restaurantData.put("mail", MAIL);
//        restaurantData.put("detail", DETAIL);
//        restaurantData.put("zone_id", ZONE.getId());
//        restaurantData.put("image_id", IMAGE_ID);
//        long id = jdbcInsert.executeAndReturnKey(restaurantData).longValue();
//
//        Optional<Restaurant> maybeRestaurant = restaurantDao.getByMail(MAIL);
//
//        assertTrue(maybeRestaurant.isPresent());
//        Restaurant restaurant = maybeRestaurant.get();
//        assertEquals(NAME, restaurant.getName());
//        assertEquals(USER_ID, restaurant.getUserID());
//        assertEquals(ADDRESS, restaurant.getAddress());
//        assertEquals(MAIL, restaurant.getMail());
//        assertEquals(DETAIL, restaurant.getDetail());
//        assertEquals(ZONE, restaurant.getZone());
//        assertEquals(IMAGE_ID, restaurant.getImageId());
//    }
//
//    @Test
//    public void testFindByMailRestaurantDoesntExist() {
//
//        Optional<Restaurant> maybeRestaurant = restaurantDao.getByMail(MAIL);
//
//        assertFalse(maybeRestaurant.isPresent());
//    }
//
//    @Test
//    public void testFindByUserIdExists() {
//        final Map<String, Object> restaurantData = new HashMap<>();
//        restaurantData.put("name", NAME);
//        restaurantData.put("user_id", USER_ID);
//        restaurantData.put("address", ADDRESS);
//        restaurantData.put("mail", MAIL);
//        restaurantData.put("detail", DETAIL);
//        restaurantData.put("zone_id", ZONE.getId());
//        restaurantData.put("image_id", IMAGE_ID);
//        long id = jdbcInsert.executeAndReturnKey(restaurantData).longValue();
//
//        Optional<Restaurant> maybeRestaurant = restaurantDao.getByUserId(USER_ID);
//
//        assertTrue(maybeRestaurant.isPresent());
//        Restaurant restaurant = maybeRestaurant.get();
//        assertEquals(NAME, restaurant.getName());
//        assertEquals(USER_ID, restaurant.getUserID());
//        assertEquals(ADDRESS, restaurant.getAddress());
//        assertEquals(MAIL, restaurant.getMail());
//        assertEquals(DETAIL, restaurant.getDetail());
//        assertEquals(ZONE, restaurant.getZone());
//        assertEquals(IMAGE_ID, restaurant.getImageId());
//    }
//
//    @Test
//    public void testFindByUserIdRestaurantDoesntExist() {
//
//        Optional<Restaurant> maybeRestaurant = restaurantDao.getByUserId(USER_ID);
//
//        assertFalse(maybeRestaurant.isPresent());
//    }
//
//    @Test
//    public void testUpdateRestaurant() {
//        final Map<String, Object> restaurantData = new HashMap<>();
//        restaurantData.put("name", NAME);
//        restaurantData.put("user_id", USER_ID);
//        restaurantData.put("address", ADDRESS);
//        restaurantData.put("mail", MAIL);
//        restaurantData.put("detail", DETAIL);
//        restaurantData.put("zone_id", ZONE.getId());
//        restaurantData.put("image_id", IMAGE_ID);
//        long id = jdbcInsert.executeAndReturnKey(restaurantData).longValue();
//
//        Optional<Restaurant> maybeRestaurant = restaurantDao.getByUserId(USER_ID);
//
//        assertTrue(maybeRestaurant.isPresent());
//        Restaurant restaurant = maybeRestaurant.get();
//        assertEquals(NAME, restaurant.getName());
//        assertEquals(USER_ID, restaurant.getUserID());
//        assertEquals(ADDRESS, restaurant.getAddress());
//        assertEquals(MAIL, restaurant.getMail());
//        assertEquals(DETAIL, restaurant.getDetail());
//        assertEquals(ZONE, restaurant.getZone());
//        assertEquals(IMAGE_ID, restaurant.getImageId());
//    }
//
//}
