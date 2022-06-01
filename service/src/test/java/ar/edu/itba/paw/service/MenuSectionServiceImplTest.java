package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.MenuSection;
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
        when(restaurantService.getByUserID(anyLong())).
                thenReturn(Optional.of(RESTAURANT));

        MenuSection menuSection = null;
        try {
            menuSection = menuSectionService.create(MENU_SECTION_NAME);
        } catch (Exception e) {
            System.out.println(e.getClass());
            Assert.fail("Unexpected error during operation create menuSection: " + e.getMessage());
        }

        Assert.assertNotNull(menuSection);
        Assert.assertEquals(RESTAURANT, menuSection.getRestaurant());
        Assert.assertTrue(RESTAURANT.getMenuSectionList().contains(menuSection));
    }

    @Test
    public void testCannotCreateIfNotAuthenticated() {
        when(securityService.getCurrentUser()).
                thenReturn(Optional.empty());

        Assert.assertThrows(UnauthenticatedUserException.class, () -> menuSectionService.create(MENU_SECTION_NAME));
    }

    @Test
    public void testCannotCreateIfNotOwner() {
        when(securityService.getCurrentUser()).
                thenReturn(Optional.of(AUX_USER));
        when(restaurantService.getByUserID(anyLong())).
                thenReturn(Optional.of(RESTAURANT));

        Assert.assertThrows(IllegalArgumentException.class, () -> menuSectionService.create(MENU_SECTION_NAME));
    }

    @Test
    public void testUpdateNameMenuSection() {
        when(securityService.getCurrentUser()).
                thenReturn(Optional.of(USER));
        when(menuSectionDao.getById(anyLong())).
                thenReturn(Optional.of(new MenuSection(MENU_SECTION_NAME, RESTAURANT)));

        try {
            menuSectionService.updateName(MENU_SECTION_ID, MENU_SECTION_NAME);
        } catch (Exception e) {
            System.out.println(e.getClass());
            Assert.fail("Unexpected error during operation create menuSection: " + e.getMessage());
        }

    }

    @Test
    public void testCannotUpdateNameIfNotAuthenticated() {
        when(securityService.getCurrentUser()).
                thenReturn(Optional.empty());

        Assert.assertThrows(UnauthenticatedUserException.class, () -> menuSectionService.updateName(MENU_SECTION_ID, MENU_SECTION_NAME));
    }

    @Test
    public void testCannotUpdateNameIfNotExists() {
        when(securityService.getCurrentUser()).
                thenReturn(Optional.of(USER));
        when(menuSectionService.getById(anyLong())).
                thenReturn(Optional.empty());

        Assert.assertThrows(IllegalArgumentException.class, () -> menuSectionService.updateName(MENU_SECTION_ID, MENU_SECTION_NAME));
    }

    @Test
    public void testCannotUpdateNameIfNotOwner() {
        when(securityService.getCurrentUser()).
                thenReturn(Optional.of(AUX_USER));
        when(menuSectionDao.getById(anyLong())).
                thenReturn(Optional.of(new MenuSection(MENU_SECTION_NAME, RESTAURANT)));

        Assert.assertThrows(IllegalArgumentException.class, () -> menuSectionService.updateName(MENU_SECTION_ID, MENU_SECTION_NAME));

    }

    @Test
    public void testDeleteMenuSection() {
        when(securityService.getCurrentUser()).
                thenReturn(Optional.of(USER));
        when(menuSectionDao.getById(anyLong())).
                thenReturn(Optional.of(new MenuSection(MENU_SECTION_NAME, RESTAURANT)));

        try {
            menuSectionService.delete(MENU_SECTION_ID);
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
                thenReturn(Optional.of(AUX_USER));
        when(menuSectionDao.getById(anyLong())).
                thenReturn(Optional.of(new MenuSection(MENU_SECTION_NAME, RESTAURANT)));

        Assert.assertThrows(IllegalArgumentException.class, () -> menuSectionService.delete(MENU_SECTION_ID));
    }

    @Test
    public void testGetByRestaurantId() {
        when(securityService.getCurrentUser()).
                thenReturn(Optional.of(USER));
        when(restaurantService.getById(anyLong())).
                thenReturn(Optional.of(RESTAURANT));
        when(restaurantService.getByUserID(anyLong())).
                thenReturn(Optional.of(RESTAURANT));

        Assert.assertTrue(menuSectionService.getByRestaurantId(RESTAURANT.getId()).isEmpty());

        MenuSection menuSection = menuSectionService.create(MENU_SECTION_NAME); // This method is already tested so we can use it here to test the getter.

        Assert.assertTrue(menuSectionService.getByRestaurantId(RESTAURANT.getId()).contains(menuSection));
    }

}
