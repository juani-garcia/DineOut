package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.MenuSection;
import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.model.exceptions.UnauthenticatedUserException;
import ar.edu.itba.paw.persistence.MenuSectionDao;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MenuSectionServiceImplTest {


    @InjectMocks
    private MenuSectionServiceImpl menuSectionService;

    @Mock
    private SecurityService securityService;
    @Mock
    private RestaurantService restaurantService;
    @Mock
    private MenuSectionDao menuSectionDao;

    @Test
    public void testCreateMenuSection() {
        when(securityService.getCurrentUser()).
                thenReturn(Optional.of(TestValues.USER));
        when(menuSectionDao.create(anyLong(), anyString())).
                thenReturn(new MenuSection(TestValues.ID, TestValues.NAME, TestValues.RESTAURANT_ID, TestValues.ORDERING));
        when(restaurantService.getById(anyLong())).
                thenReturn(Optional.of(TestValues.RESTAURANT));

        MenuSection menuSection = null;
        try {
            menuSection = menuSectionService.create(TestValues.RESTAURANT_ID, TestValues.NAME);
        } catch (Exception e) {
            System.out.println(e.getClass());
            Assert.fail("Unexpected error during operation create menuSection: " + e.getMessage());
        }

        Assert.assertNotNull(menuSection);
        Assert.assertEquals(TestValues.ID, menuSection.getId());
    }

    @Test
    public void testCannotCreateIfNotAuthenticated() {
        when(securityService.getCurrentUser()).
                thenReturn(Optional.empty());

        Assert.assertThrows(UnauthenticatedUserException.class, () -> menuSectionService.create(TestValues.RESTAURANT_ID, TestValues.NAME));
    }

    @Test
    public void testCannotCreateIfNotOwner() {
        when(securityService.getCurrentUser()).
                thenReturn(Optional.of(new User(TestValues.USER_ID + 1, TestValues.USER_USERNAME, TestValues.USER_PASSWORD, TestValues.USER_FIRST_NAME, TestValues.USER_LAST_NAME)));
        when(restaurantService.getById(anyLong())).
                thenReturn(Optional.of(TestValues.RESTAURANT));

        Assert.assertThrows(IllegalArgumentException.class, () -> menuSectionService.create(TestValues.RESTAURANT_ID, TestValues.NAME));
    }

    @Test
    public void testUpdateNameMenuSection() {
        when(securityService.getCurrentUser()).
                thenReturn(Optional.of(TestValues.USER));
        when(restaurantService.getById(anyLong())).
                thenReturn(Optional.of(TestValues.RESTAURANT));
        when(menuSectionDao.getById(anyLong())).
                thenReturn(Optional.of(new MenuSection(TestValues.ID, TestValues.NAME, TestValues.RESTAURANT_ID, TestValues.ORDERING)));

        try {
            menuSectionService.updateName(TestValues.ID, TestValues.NAME);
        } catch (Exception e) {
            System.out.println(e.getClass());
            Assert.fail("Unexpected error during operation create menuSection: " + e.getMessage());
        }

    }

    @Test
    public void testCannotUpdateNameIfNotAuthenticated() {
        when(securityService.getCurrentUser()).
                thenReturn(Optional.empty());

        Assert.assertThrows(UnauthenticatedUserException.class, () -> menuSectionService.updateName(TestValues.ID, TestValues.NAME));
    }

    @Test
    public void testCannotUpdateNameIfNotExists() {
        when(securityService.getCurrentUser()).
                thenReturn(Optional.of(TestValues.USER));
        when(menuSectionService.getById(anyLong())).
                thenReturn(Optional.empty());

        Assert.assertThrows(IllegalArgumentException.class, () -> menuSectionService.updateName(TestValues.ID, TestValues.NAME));
    }

    @Test
    public void testCannotUpdateNameIfNotOwner() {
        when(securityService.getCurrentUser()).
                thenReturn(Optional.of(new User(TestValues.USER_ID + 1, TestValues.USER_USERNAME, TestValues.USER_PASSWORD, TestValues.USER_FIRST_NAME, TestValues.USER_LAST_NAME)));
        when(menuSectionDao.getById(anyLong())).
                thenReturn(Optional.of(new MenuSection(TestValues.ID, TestValues.NAME, TestValues.RESTAURANT_ID, TestValues.ORDERING)));
        when(restaurantService.getById(anyLong())).
                thenReturn(Optional.of(TestValues.RESTAURANT));

        Assert.assertThrows(IllegalArgumentException.class, () -> menuSectionService.updateName(TestValues.ID, TestValues.NAME));

    }

    @Test
    public void testDeleteMenuSection() {
        when(securityService.getCurrentUser()).
                thenReturn(Optional.of(TestValues.USER));
        when(restaurantService.getById(anyLong())).
                thenReturn(Optional.of(TestValues.RESTAURANT));
        when(menuSectionDao.getById(anyLong())).
                thenReturn(Optional.of(new MenuSection(TestValues.ID, TestValues.NAME, TestValues.RESTAURANT_ID, TestValues.ORDERING)));

        try {
            menuSectionService.delete(TestValues.ID);
        } catch (Exception e) {
            System.out.println(e.getClass());
            Assert.fail("Unexpected error during operation create menuSection: " + e.getMessage());
        }

    }

    @Test
    public void testCannotDeleteIfNotAuthenticated() {
        when(securityService.getCurrentUser()).
                thenReturn(Optional.empty());

        Assert.assertThrows(UnauthenticatedUserException.class, () -> menuSectionService.delete(TestValues.RESTAURANT_ID));
    }

    @Test
    public void testCannotDeleteIfNotExists() {
        when(securityService.getCurrentUser()).
                thenReturn(Optional.of(TestValues.USER));
        when(menuSectionDao.getById(anyLong())).
                thenReturn(Optional.empty());

        Assert.assertThrows(IllegalArgumentException.class, () -> menuSectionService.delete(TestValues.RESTAURANT_ID));
    }

    @Test
    public void testCannotDeleteIfNotOwner() {
        when(securityService.getCurrentUser()).
                thenReturn(Optional.of(new User(TestValues.USER_ID + 1, TestValues.USER_USERNAME, TestValues.USER_PASSWORD, TestValues.USER_FIRST_NAME, TestValues.USER_LAST_NAME)));
        when(menuSectionDao.getById(anyLong())).
                thenReturn(Optional.of(new MenuSection(TestValues.ID, TestValues.NAME, TestValues.RESTAURANT_ID, TestValues.ORDERING)));
        when(restaurantService.getById(anyLong())).
                thenReturn(Optional.of(TestValues.RESTAURANT));

        Assert.assertThrows(IllegalArgumentException.class, () -> menuSectionService.delete(TestValues.ID));

    }


}
