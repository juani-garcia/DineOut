package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.*;
import ar.edu.itba.paw.model.Image;
import ar.edu.itba.paw.model.MenuItem;
import ar.edu.itba.paw.model.exceptions.NotFoundException;
import ar.edu.itba.paw.model.exceptions.UnauthenticatedUserException;
import ar.edu.itba.paw.persistence.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class MenuItemServiceImpl implements MenuItemService {

    @Autowired
    private SecurityService securityService;

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private MenuSectionService menuSectionService;

    @Autowired
    private ImageService imageService;

    @Autowired
    private MenuItemDao menuItemDao;
    private static final Logger LOGGER = LoggerFactory.getLogger(MenuItemServiceImpl.class);

    @Override
    public Optional<MenuItem> getById(final long menuItemId) {
        return menuItemDao.getById(menuItemId);
    }

    @Override
    public List<MenuItem> getBySectionId(final long menuSectionId) {
        MenuSection menuSection = menuSectionService.getById(menuSectionId).orElseThrow(NotFoundException::new);
        return menuSection.getMenuItemList();
    }

    @Transactional
    @Override
    public MenuItem create(final String name, final String detail,
                           final double price, final long menuSectionId, final byte[] imageBytes) {
        User user = securityService.getCurrentUser().orElseThrow(UnauthenticatedUserException::new);
        MenuSection menuSection = menuSectionService.getById(menuSectionId).orElseThrow(IllegalArgumentException::new);
        Restaurant restaurant = menuSection.getRestaurant();
        if (!Objects.equals(user.getId(), restaurant.getUser().getId()))
            throw new IllegalArgumentException("Cannot create item in someone else's restaurant");
        Image image = null;
        if (imageBytes != null && imageBytes.length > 0) {
            image = imageService.create(imageBytes);
        }
        return menuSection.addMenuItem(name, detail, price, image);
    }

    @Transactional
    @Override
    public void delete(final long menuItemId) {
        MenuItem menuItem = validateItem(menuItemId); // TODO: Check on cascade with image
        menuItem.getSection().getMenuItemList().remove(menuItem);
        menuItemDao.delete(menuItemId);
    }

    @Transactional
    @Override
    public void edit(final long menuItemId, final String name,
                     final String detail, final double price,
                     final long menuSectionId) {
        final MenuItem menuItem = validateItem(menuItemId);
        final MenuSection menuSection = menuSectionService.getById(menuSectionId).orElseThrow(IllegalArgumentException::new);

        menuItem.setName(name);
        menuItem.setDetail(detail);
        menuItem.setPrice(price);
        menuItem.setSection(menuSection);
    }

    protected void move(final long menuItemId, final boolean moveUp) {
        MenuItem menuItem = validateItem(menuItemId);
        MenuSection menuSection = menuItem.getSection();
        List<MenuItem> menuItemList = menuSection.getMenuItemList();
        int index = menuItemList.indexOf(menuItem);
        if ((moveUp && index == 0) || (!moveUp && index == menuItemList.size() - 1))
            throw new IllegalArgumentException();

        Collections.swap(menuItemList, index, index + (moveUp ? -1 : 1));
    }

    @Transactional
    @Override
    public void moveUp(final long menuItemId) {
        move(menuItemId, true);
    }

    @Transactional
    @Override
    public void moveDown(final long menuItemId) {
        move(menuItemId, false);
    }

    protected MenuItem validateItem(final long itemId) {
        User user = securityService.getCurrentUser().orElseThrow(UnauthenticatedUserException::new);
        MenuItem menuItem = menuItemDao.getById(itemId).orElseThrow(IllegalArgumentException::new);
        MenuSection menuSection = menuSectionService.getById(menuItem.getSection().getId()).orElseThrow(IllegalStateException::new);
        Restaurant restaurant = menuSection.getRestaurant();
        if (!Objects.equals(restaurant.getUser().getId(), user.getId()))
            throw new IllegalArgumentException("Cannot edit someone else's item");
        return menuItem;
    }

    @Transactional
    @Override
    public void updateImage(final long menuItemId, final byte[] image) {
        final MenuItem menuItem = validateItem(menuItemId);
        Image oldImage = menuItem.getImage();
        if (image != null && image.length > 0) { // There is new image
            if (oldImage == null) { // There is no old image
                LOGGER.debug("Creating image for menu item {}", menuItemId);
                menuItem.setImage(imageService.create(image));
                LOGGER.debug("New image id: {}", menuItem.getImage().getId());
            } else {
                LOGGER.debug("Updating image for menu item {}", menuItemId);
                imageService.edit(oldImage.getId(), image);
            }
        } else { // No new image
            if (oldImage != null) {
                LOGGER.debug("Deleting image for menu item {}", menuItemId);
                menuItem.setImage(null);
                imageService.delete(oldImage.getId());
            }
        }
    }

}
