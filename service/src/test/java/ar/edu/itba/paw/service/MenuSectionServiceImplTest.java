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

import static ar.edu.itba.paw.service.TestValues.*;
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


}
