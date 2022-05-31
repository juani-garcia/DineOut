package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.*;
import ar.edu.itba.paw.model.exceptions.UnauthenticatedUserException;
import ar.edu.itba.paw.persistence.*;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class RestaurantServiceImplTest {

    private static final long ID = 1;
    private static final long USER_ID = 1;
    private static final String NAME = "Restaurant";
    private static final long IMAGE_ID = 1;
    private static final String ADDRESS = "Address";
    private static final String MAIL = "restaurant@mail.com";
    private static final String DETAIL = "Detail";
    private static final Zone ZONE = Zone.ACASSUSO;
    private static final long FAV_COUNT = 0;
    private static final List<Long> CATEGORIES = Arrays.asList(Category.AMERICAN.getId(), Category.JAPANESE.getId());
    private static final List<Shift> SHIFTS = Arrays.asList(Shift.MORNING, Shift.NOON);
    private static final List<Long> SHIFTS_IDS = SHIFTS.stream().map(Shift::getId).collect(Collectors.toList());

    private static final String USER_USERNAME = "user@mail.com";
    private static final String USER_PASSWORD = "1234567890User";
    private static final String USER_FIRST_NAME = "John";
    private static final String USER_LAST_NAME = "Doe";
    private static final User USER = new User(USER_ID, USER_USERNAME,
            USER_PASSWORD, USER_FIRST_NAME, USER_LAST_NAME);

    @InjectMocks
    private RestaurantServiceImpl restaurantService;

    @Mock
    private RestaurantDao restaurantDao;
    @Mock
    private SecurityService securityService;
    @Mock
    private FavoriteService favoriteService;
    @Mock
    private ImageService imageService;

    @Test
    public void testCreateRestaurant() {
        when(restaurantDao.create(any(), anyString(), any(), anyString(), anyString(), anyString(), any())).
                thenReturn(new Restaurant(USER, NAME, IMAGE_ID, ADDRESS, MAIL, DETAIL, ZONE, FAV_COUNT));
        when(securityService.getCurrentUser()).
                thenReturn(Optional.of(USER));

        Restaurant restaurant = null;
        try {
            restaurant = restaurantService.create(NAME, null, ADDRESS, MAIL, DETAIL, ZONE, CATEGORIES, SHIFTS_IDS);
        } catch (Exception e) {
            System.out.println(e.getClass());
            Assert.fail("Unexpected error during operation create restaurant: " + e.getMessage());
        }

        Assert.assertNotNull(restaurant);
        Assert.assertEquals(ID, restaurant.getId());
        Assert.assertTrue(restaurant.getShifts().containsAll(SHIFTS));
    }

    @Test
    public void testCannotCreateIfAnonymous() {
        when(securityService.getCurrentUser()).
                thenReturn(Optional.empty());

        Assert.assertThrows(UnauthenticatedUserException.class, () -> restaurantService.create(NAME, null, ADDRESS, MAIL, DETAIL, ZONE, CATEGORIES, SHIFTS_IDS));
    }

    @Test
    public void testCannotCreateIfAlreadyOwner() {
        when(securityService.getCurrentUser()).
                thenReturn(Optional.of(USER));
        when(restaurantDao.getByUserId(anyLong())).
                thenReturn(Optional.of(new Restaurant(USER, NAME, IMAGE_ID, ADDRESS, MAIL, DETAIL, ZONE, FAV_COUNT)));

        Assert.assertThrows(IllegalStateException.class, () -> restaurantService.create(NAME, null, ADDRESS, MAIL, DETAIL, ZONE, CATEGORIES, SHIFTS_IDS));
    }

    @Test
    public void testImageIsCreated() {
        when(securityService.getCurrentUser()).
                thenReturn(Optional.of(USER));
        when(restaurantDao.getByUserId(anyLong())).
                thenReturn(Optional.empty());
        when(restaurantDao.create(any(), anyString(), any(), anyString(), anyString(), anyString(), any())).
                thenReturn(new Restaurant(USER, NAME, IMAGE_ID, ADDRESS, MAIL, DETAIL, ZONE, FAV_COUNT));
        when(imageService.create(any())).
                thenReturn(new Image(1, new byte[] {0, 1, 0, 1}));

        Restaurant restaurant = restaurantService.create(NAME, new byte[] {0}, ADDRESS, MAIL, DETAIL, ZONE, CATEGORIES, SHIFTS_IDS);

        Assert.assertNotNull(restaurant);
        Assert.assertNotNull(restaurant.getImageId());
        Assert.assertNotEquals(Long.valueOf(0), restaurant.getImageId());
    }

}
