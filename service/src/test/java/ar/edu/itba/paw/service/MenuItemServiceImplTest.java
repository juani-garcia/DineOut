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

import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class MenuItemServiceImplTest {

    private static final long ID = 1;
    private static final String NAME = "Restaurant";
    private static final String DETAIL = "Detail";
    private static final double PRICE = 5.0;
    private static final long SECTION_ID = 1;
    private static final long ORDERING = 1;
    private static final byte[] IMAGE_BYTES = new byte[] {0, 1};
    private static final long IMAGE_ID = 1;

    private static final long USER_ID = 1;
    private static final String USER_USERNAME = "user@mail.com";
    private static final String USER_PASSWORD = "1234567890User";
    private static final String USER_FIRST_NAME = "John";
    private static final String USER_LAST_NAME = "Doe";
    private static final User USER = new User(USER_ID, USER_USERNAME,
            USER_PASSWORD, USER_FIRST_NAME, USER_LAST_NAME);

    private static final long RESTAURANT_ID = 1;
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

    private static final String SECTION_NAME = "Section";
    private static final long SECTION_RESTAURANT_ID = RESTAURANT_ID;
    private static final long SECTION_ORDERING = 1;
    private static final MenuSection MENU_SECTION = new MenuSection(SECTION_ID,
            SECTION_NAME, SECTION_RESTAURANT_ID, SECTION_ORDERING);

    @InjectMocks
    private MenuItemServiceImpl menuItemService;

    @Mock
    private SecurityService securityService;
    @Mock
    private RestaurantService restaurantService;
    @Mock
    private MenuSectionService menuSectionService;
    @Mock
    private ImageService imageService;
    @Mock
    private MenuItemDao menuItemDao;

    @Test
    public void testCreateMenuItem() {
        when(securityService.getCurrentUser()).
                thenReturn(Optional.of(USER));
        when(menuItemDao.create(anyString(), any(), anyDouble(), anyLong(), any())).
                thenReturn(new MenuItem(ID, NAME, DETAIL, PRICE, SECTION_ID, ORDERING, IMAGE_ID));
        when(menuSectionService.getById(anyLong())).
                thenReturn(Optional.of(MENU_SECTION));
        when(restaurantService.getById(anyLong())).
                thenReturn(Optional.of(RESTAURANT));

        MenuItem menuItem = null;
        try {
            menuItem = menuItemService.create(NAME, DETAIL, PRICE, SECTION_ID, IMAGE_BYTES);
        } catch (Exception e) {
            System.out.println(e.getClass());
            Assert.fail("Unexpected error during operation create menuItem: " + e.getMessage());
        }

        Assert.assertNotNull(menuItem);
        Assert.assertEquals(ID, menuItem.getId());
    }

    @Test
    public void testCannotCreateIfNotAuthenticated() {
        when(securityService.getCurrentUser()).
                thenReturn(Optional.empty());

        Assert.assertThrows(UnauthenticatedUserException.class, () -> menuItemService.create(NAME, DETAIL, PRICE, SECTION_ID, IMAGE_BYTES));
    }

    @Test
    public void testCannotCreateIfNotOwner() {
        when(securityService.getCurrentUser()).
                thenReturn(Optional.of(new User(USER_ID + 1, USER_USERNAME, USER_PASSWORD, USER_FIRST_NAME, USER_LAST_NAME)));
        when(menuSectionService.getById(anyLong())).
                thenReturn(Optional.of(MENU_SECTION));
        when(restaurantService.getById(anyLong())).
                thenReturn(Optional.of(RESTAURANT));

        Assert.assertThrows(IllegalArgumentException.class, () -> menuItemService.create(NAME, DETAIL, PRICE, SECTION_ID, IMAGE_BYTES));
    }

    @Test
    public void testEditMenuItem() {
        when(securityService.getCurrentUser()).
                thenReturn(Optional.of(USER));
        when(menuSectionService.getById(anyLong())).
                thenReturn(Optional.of(MENU_SECTION));
        when(restaurantService.getById(anyLong())).
                thenReturn(Optional.of(RESTAURANT));
        when(menuItemDao.getById(anyLong())).
                thenReturn(Optional.of(new MenuItem(ID, NAME, DETAIL, PRICE, SECTION_ID, ORDERING, IMAGE_ID)));
        when(imageService.create(any())).
                thenReturn(new Image(1, new byte[] {0, 1}));

        try {
            menuItemService.edit(ID, NAME, DETAIL, PRICE, SECTION_ID, IMAGE_BYTES);
        } catch (Exception e) {
            System.out.println(e.getClass());
            Assert.fail("Unexpected error during operation create menuItem: " + e.getMessage());
        }

    }

    @Test
    public void testCannotEditIfNotAuthenticated() {
        when(securityService.getCurrentUser()).
                thenReturn(Optional.empty());

        Assert.assertThrows(UnauthenticatedUserException.class, () -> menuItemService.edit(ID, NAME, DETAIL, PRICE, SECTION_ID, IMAGE_BYTES));
    }

    @Test
    public void testCannotEditIfNotExists() {
        when(securityService.getCurrentUser()).
                thenReturn(Optional.of(USER));
        when(menuItemDao.getById(anyLong())).
                thenReturn(Optional.empty());

        Assert.assertThrows(IllegalArgumentException.class, () -> menuItemService.edit(ID, NAME, DETAIL, PRICE, SECTION_ID, IMAGE_BYTES));
    }

    @Test
    public void testCannotEditIfNotOwner() {
        when(securityService.getCurrentUser()).
                thenReturn(Optional.of(new User(USER_ID + 1, USER_USERNAME, USER_PASSWORD, USER_FIRST_NAME, USER_LAST_NAME)));
        when(menuItemService.getById(anyLong())).
                thenReturn(Optional.of(new MenuItem(ID, NAME, DETAIL, PRICE, SECTION_ID, ORDERING, IMAGE_ID)));
        when(menuSectionService.getById(anyLong())).
                thenReturn(Optional.of(MENU_SECTION));
        when(restaurantService.getById(anyLong())).
                thenReturn(Optional.of(RESTAURANT));

        Assert.assertThrows(IllegalArgumentException.class, () -> menuItemService.edit(ID, NAME, DETAIL, PRICE, SECTION_ID, IMAGE_BYTES));

    }

    @Test
    public void testDeleteMenuItem() {
        when(securityService.getCurrentUser()).
                thenReturn(Optional.of(USER));
        when(menuSectionService.getById(anyLong())).
                thenReturn(Optional.of(MENU_SECTION));
        when(restaurantService.getById(anyLong())).
                thenReturn(Optional.of(RESTAURANT));
        when(menuItemDao.getById(anyLong())).
                thenReturn(Optional.of(new MenuItem(ID, NAME, DETAIL, PRICE, SECTION_ID, ORDERING, IMAGE_ID)));

        try {
            menuItemService.delete(ID);
        } catch (Exception e) {
            System.out.println(e.getClass());
            Assert.fail("Unexpected error during operation create menuItem: " + e.getMessage());
        }

    }

    @Test
    public void testCannotDeleteIfNotAuthenticated() {
        when(securityService.getCurrentUser()).
                thenReturn(Optional.empty());

        Assert.assertThrows(UnauthenticatedUserException.class, () -> menuItemService.create(NAME, DETAIL, PRICE, SECTION_ID, IMAGE_BYTES));
    }

    @Test
    public void testCannotDeleteIfNotExists() {
        when(securityService.getCurrentUser()).
                thenReturn(Optional.of(USER));
        when(menuItemDao.getById(anyLong())).
                thenReturn(Optional.empty());

        Assert.assertThrows(IllegalArgumentException.class, () -> menuItemService.delete(ID));
    }

    @Test
    public void testCannotDeleteIfNotOwner() {
        when(securityService.getCurrentUser()).
                thenReturn(Optional.of(new User(USER_ID + 1, USER_USERNAME, USER_PASSWORD, USER_FIRST_NAME, USER_LAST_NAME)));
        when(menuItemService.getById(anyLong())).
                thenReturn(Optional.of(new MenuItem(ID, NAME, DETAIL, PRICE, SECTION_ID, ORDERING, IMAGE_ID)));
        when(menuSectionService.getById(anyLong())).
                thenReturn(Optional.of(MENU_SECTION));
        when(restaurantService.getById(anyLong())).
                thenReturn(Optional.of(RESTAURANT));

        Assert.assertThrows(IllegalArgumentException.class, () -> menuItemService.delete(ID));

    }

    @Test
    public void testImageIsCreated() {
        when(securityService.getCurrentUser()).
                thenReturn(Optional.of(USER));
        when(menuItemDao.create(anyString(), any(), anyDouble(), anyLong(), any())).
                thenReturn(new MenuItem(ID, NAME, DETAIL, PRICE, SECTION_ID, ORDERING, IMAGE_ID));
        when(menuSectionService.getById(anyLong())).
                thenReturn(Optional.of(MENU_SECTION));
        when(restaurantService.getById(anyLong())).
                thenReturn(Optional.of(RESTAURANT));

        MenuItem menuItem = menuItemService.create(NAME, DETAIL, PRICE, SECTION_ID, new byte[] {0, 1});

        Assert.assertNotNull(menuItem);
        Assert.assertNotNull(menuItem.getImageId());
        Assert.assertNotEquals(Long.valueOf(0), menuItem.getImageId());
    }


}
