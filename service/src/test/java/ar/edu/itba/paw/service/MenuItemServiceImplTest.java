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

import static ar.edu.itba.paw.service.TestValues.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class MenuItemServiceImplTest {
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
        when(menuSectionService.getById(anyLong())).
                thenReturn(Optional.of(MENU_SECTION));

        MenuItem menuItem = null;
        try {
            menuItem = menuItemService.create(MENU_ITEM_NAME, DETAIL, PRICE, MENU_SECTION_ID, IMAGE_BYTES);
        } catch (Exception e) {
            System.out.println(e.getClass());
            Assert.fail("Unexpected error during operation create menuItem: " + e.getMessage());
        }

        Assert.assertNotNull(menuItem);
        Assert.assertTrue(MENU_SECTION.getMenuItemList().contains(menuItem));
    }

    @Test
    public void testCannotCreateIfNotAuthenticated() {
        when(securityService.getCurrentUser()).
                thenReturn(Optional.empty());

        Assert.assertThrows(UnauthenticatedUserException.class, () -> menuItemService.create(MENU_ITEM_NAME, DETAIL, PRICE, MENU_SECTION_ID, IMAGE_BYTES));
    }

    @Test
    public void testCannotCreateIfNotOwner() {
        when(securityService.getCurrentUser()).
                thenReturn(Optional.of(AUX_USER));
        when(menuSectionService.getById(anyLong())).
                thenReturn(Optional.of(MENU_SECTION));

        Assert.assertThrows(IllegalArgumentException.class, () -> menuItemService.create(MENU_ITEM_NAME, DETAIL, PRICE, MENU_SECTION_ID, IMAGE_BYTES));
    }

    @Test
    public void testEditMenuItem() {
        when(securityService.getCurrentUser()).
                thenReturn(Optional.of(USER));
        when(menuSectionService.getById(anyLong())).
                thenReturn(Optional.of(MENU_SECTION));
        when(menuItemDao.getById(anyLong())).
                thenReturn(Optional.of(MENU_ITEM));
        when(imageService.create(any())).
                thenReturn(MENU_ITEM_IMAGE);

        try {
            menuItemService.edit(MENU_ITEM_ID, MENU_ITEM_NAME, DETAIL, PRICE, MENU_SECTION_ID);
        } catch (Exception e) {
            System.out.println(e.getClass());
            Assert.fail("Unexpected error during operation create menuItem: " + e.getMessage());
        }

    }

    @Test
    public void testCannotEditIfNotAuthenticated() {
        when(securityService.getCurrentUser()).
                thenReturn(Optional.empty());

        Assert.assertThrows(UnauthenticatedUserException.class, () -> menuItemService.edit(MENU_ITEM_ID, MENU_ITEM_NAME, DETAIL, PRICE, MENU_SECTION_ID));
    }

    @Test
    public void testCannotEditIfNotExists() {
        when(securityService.getCurrentUser()).
                thenReturn(Optional.of(USER));
        when(menuItemDao.getById(anyLong())).
                thenReturn(Optional.empty());

        Assert.assertThrows(IllegalArgumentException.class, () -> menuItemService.edit(MENU_ITEM_ID, MENU_ITEM_NAME, DETAIL, PRICE, MENU_SECTION_ID));
    }

    @Test
    public void testCannotEditIfNotOwner() {
        when(securityService.getCurrentUser()).
                thenReturn(Optional.of(AUX_USER));
        when(menuItemService.getById(anyLong())).
                thenReturn(Optional.of(MENU_ITEM));
        when(menuSectionService.getById(anyLong())).
                thenReturn(Optional.of(MENU_SECTION));

        Assert.assertThrows(IllegalArgumentException.class, () -> menuItemService.edit(MENU_ITEM_ID, MENU_ITEM_NAME, DETAIL, PRICE, MENU_SECTION_ID));

    }

    @Test
    public void testDeleteMenuItem() {
        when(securityService.getCurrentUser()).
                thenReturn(Optional.of(USER));
        when(menuSectionService.getById(anyLong())).
                thenReturn(Optional.of(MENU_SECTION));
        when(menuItemDao.getById(anyLong())).
                thenReturn(Optional.of(MENU_ITEM));

        try {
            menuItemService.delete(MENU_ITEM_ID);
        } catch (Exception e) {
            System.out.println(e.getClass());
            Assert.fail("Unexpected error during operation create menuItem: " + e.getMessage());
        }

    }

    @Test
    public void testCannotDeleteIfNotAuthenticated() {
        when(securityService.getCurrentUser()).
                thenReturn(Optional.empty());

        Assert.assertThrows(UnauthenticatedUserException.class, () -> menuItemService.create(MENU_ITEM_NAME, DETAIL, PRICE, MENU_SECTION_ID, IMAGE_BYTES));
    }

    @Test
    public void testCannotDeleteIfNotExists() {
        when(securityService.getCurrentUser()).
                thenReturn(Optional.of(USER));
        when(menuItemDao.getById(anyLong())).
                thenReturn(Optional.empty());

        Assert.assertThrows(IllegalArgumentException.class, () -> menuItemService.delete(MENU_ITEM_ID));
    }

    @Test
    public void testCannotDeleteIfNotOwner() {
        when(securityService.getCurrentUser()).
                thenReturn(Optional.of(AUX_USER));
        when(menuItemService.getById(anyLong())).
                thenReturn(Optional.of(MENU_ITEM));
        when(menuSectionService.getById(anyLong())).
                thenReturn(Optional.of(MENU_SECTION));

        Assert.assertThrows(IllegalArgumentException.class, () -> menuItemService.delete(MENU_ITEM_ID));

    }

}
