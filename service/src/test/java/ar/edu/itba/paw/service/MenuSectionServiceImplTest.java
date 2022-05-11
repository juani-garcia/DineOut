package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.Zone;
import ar.edu.itba.paw.model.exceptions.UnauthenticatedUserException;
import ar.edu.itba.paw.persistence.*;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MenuSectionServiceImplTest {

    private static final long ID = 1;
    private static final String NAME = "Restaurant";
    private static final long RESTAURANT_ID = 1;
    private static final Long ORDERING = 1L;

    private static final long USER_ID = 1;
    private static final String USER_USERNAME = "user@mail.com";
    private static final String USER_PASSWORD = "1234567890User";
    private static final String USER_FIRST_NAME = "John";
    private static final String USER_LAST_NAME = "Doe";
    private static final User USER = new User(USER_ID, USER_USERNAME,
            USER_PASSWORD, USER_FIRST_NAME, USER_LAST_NAME);

    private static final String RESTAURANT_NAME = "Restaurant";
    private static final Long RESTAURANT_IMAGE_ID = null;
    private static final String RESTAURANT_ADDRESS = "Address";
    private static final String RESTAURANT_MAIL = "restaurant@mail.com";
    private static final String RESTAURANT_DETAIL = null;
    private static final Zone RESTAURANT_ZONE = Zone.ACASSUSO;
    private static final Long RESTAURANT_FAV_COUNT = 0L;
    private static final Restaurant RESTAURANT = new Restaurant(RESTAURANT_ID,
            USER_ID, RESTAURANT_NAME, RESTAURANT_IMAGE_ID, RESTAURANT_ADDRESS, RESTAURANT_MAIL,
            RESTAURANT_DETAIL, RESTAURANT_ZONE, RESTAURANT_FAV_COUNT);

    @InjectMocks
    private MenuSectionServiceImpl menuSectionService;

    @Mock
    private SecurityService securityService;
    @Mock
    private RestaurantService restaurantService;
    @Mock
    private MenuSectionDao menuSectionDao;
    @Mock
    private MenuItemService menuItemService;

    @Test
    public void testCreateMenuSection() {
        when(securityService.getCurrentUser()).
                thenReturn(Optional.of(USER));
        when(menuSectionDao.create(anyLong(), anyString())).
                thenReturn(new MenuSection(ID, NAME, RESTAURANT_ID, ORDERING));
        when(restaurantService.getById(anyLong())).
                thenReturn(Optional.of(RESTAURANT));

        MenuSection menuSection = null;
        try {
            menuSection = menuSectionService.create(RESTAURANT_ID, NAME);
        } catch (Exception e) {
            System.out.println(e.getClass());
            Assert.fail("Unexpected error during operation create menuSection: " + e.getMessage());
        }

        Assert.assertNotNull(menuSection);
        Assert.assertEquals(ID, menuSection.getId());
    }

    @Test
    public void testCannotCreateIfNotAuthenticated() {
        when(securityService.getCurrentUser()).
                thenReturn(Optional.empty());

        Assert.assertThrows(UnauthenticatedUserException.class, () -> menuSectionService.create(RESTAURANT_ID, NAME));
    }
    @Test
    public void testCannotCreateIfNotOwner() {
        when(securityService.getCurrentUser()).
                thenReturn(Optional.of(new User(USER_ID + 1, USER_USERNAME, USER_PASSWORD, USER_FIRST_NAME, USER_LAST_NAME)));
        when(restaurantService.getById(anyLong())).
                thenReturn(Optional.of(RESTAURANT));

        Assert.assertThrows(IllegalArgumentException.class, () -> menuSectionService.create(RESTAURANT_ID, NAME));
    }

    @Test
    public void testUpdateNameMenuSection() {
        when(securityService.getCurrentUser()).
                thenReturn(Optional.of(USER));
        when(restaurantService.getById(anyLong())).
                thenReturn(Optional.of(RESTAURANT));
        when(menuSectionDao.getById(anyLong())).
                thenReturn(Optional.of(new MenuSection(ID, NAME, RESTAURANT_ID, ORDERING)));

        try {
            menuSectionService.updateName(ID, NAME);
        } catch (Exception e) {
            System.out.println(e.getClass());
            Assert.fail("Unexpected error during operation create menuSection: " + e.getMessage());
        }

    }

    @Test
    public void testCannotUpdateNameIfNotAuthenticated() {
        when(securityService.getCurrentUser()).
                thenReturn(Optional.empty());

        Assert.assertThrows(UnauthenticatedUserException.class, () -> menuSectionService.updateName(ID, NAME));
    }

    @Test
    public void testCannotUpdateNameIfNotExists() {
        when(securityService.getCurrentUser()).
                thenReturn(Optional.of(USER));
        when(menuSectionService.getById(anyLong())).
                thenReturn(Optional.empty());

        Assert.assertThrows(IllegalArgumentException.class, () -> menuSectionService.updateName(ID, NAME));
    }

    @Test
    public void testCannotUpdateNameIfNotOwner() {
        when(securityService.getCurrentUser()).
                thenReturn(Optional.of(new User(USER_ID + 1, USER_USERNAME, USER_PASSWORD, USER_FIRST_NAME, USER_LAST_NAME)));
        when(menuSectionDao.getById(anyLong())).
                thenReturn(Optional.of(new MenuSection(ID, NAME, RESTAURANT_ID, ORDERING)));
        when(restaurantService.getById(anyLong())).
                thenReturn(Optional.of(RESTAURANT));

        Assert.assertThrows(IllegalArgumentException.class, () -> menuSectionService.updateName(ID, NAME));

    }

    @Test
    public void testDeleteMenuSection() {
        when(securityService.getCurrentUser()).
                thenReturn(Optional.of(USER));
        when(restaurantService.getById(anyLong())).
                thenReturn(Optional.of(RESTAURANT));
        when(menuSectionDao.getById(anyLong())).
                thenReturn(Optional.of(new MenuSection(ID, NAME, RESTAURANT_ID, ORDERING)));

        try {
            menuSectionService.delete(ID);
        } catch (Exception e) {
            System.out.println(e.getClass());
            Assert.fail("Unexpected error during operation create menuSection: " + e.getMessage());
        }

    }

    @Test
    public void testCannotDeleteIfNotAuthenticated() {
        when(securityService.getCurrentUser()).
                thenReturn(Optional.empty());

        Assert.assertThrows(UnauthenticatedUserException.class, () -> menuSectionService.delete(RESTAURANT_ID));
    }

    @Test
    public void testCannotDeleteIfNotExists() {
        when(securityService.getCurrentUser()).
                thenReturn(Optional.of(USER));
        when(menuSectionDao.getById(anyLong())).
                thenReturn(Optional.empty());

        Assert.assertThrows(IllegalArgumentException.class, () -> menuSectionService.delete(RESTAURANT_ID));
    }

    @Test
    public void testCannotDeleteIfNotOwner() {
        when(securityService.getCurrentUser()).
                thenReturn(Optional.of(new User(USER_ID + 1, USER_USERNAME, USER_PASSWORD, USER_FIRST_NAME, USER_LAST_NAME)));
        when(menuSectionDao.getById(anyLong())).
                thenReturn(Optional.of(new MenuSection(ID, NAME, RESTAURANT_ID, ORDERING)));
        when(restaurantService.getById(anyLong())).
                thenReturn(Optional.of(RESTAURANT));

        Assert.assertThrows(IllegalArgumentException.class, () -> menuSectionService.delete(ID));

    }

    // TODO: Test triggers


}
