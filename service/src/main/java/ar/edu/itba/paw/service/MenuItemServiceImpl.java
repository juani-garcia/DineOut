package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.exceptions.ForbiddenActionException;
import ar.edu.itba.paw.model.exceptions.UnauthenticatedUserException;
import ar.edu.itba.paw.persistence.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
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

    @Override
    public Optional<MenuItem> getById(final long itemId) {
        return menuItemDao.getById(itemId);
    }

    @Override
    public List<MenuItem> getBySectionId(long sectionId) {
        return menuItemDao.getBySectionId(sectionId);
    }

    @Override
    public MenuItem create(String name, String detail, double price, long sectionId, byte[] imageBytes) {
        Image image = null;
        if (imageBytes != null && imageBytes.length > 0) {
            image = imageService.create(imageBytes);
        }
        return menuItemDao.create(name, detail, price, sectionId, (image != null) ? image.getId() : null);
    }

    @Override
    public boolean delete(final long itemId) {
        MenuItem menuItem = getById(itemId).orElseThrow(() -> new RuntimeException("Invalid itemId"));  // TODO: Modularizar
        MenuSection menuSection = menuSectionService.getById(menuItem.getSectionId()).orElseThrow(IllegalStateException::new);
        Restaurant restaurant = restaurantService.getById(menuSection.getRestaurantId()).orElseThrow(IllegalStateException::new);
        if (restaurant.getUserID() != securityService.getCurrentUser().orElseThrow(IllegalStateException::new).getId())
            throw new RuntimeException("Invalid permissions");
        return menuItemDao.delete(itemId);
    }

    public boolean edit(long itemId, String name, String detail, double price, long sectionId, long ordering, byte[] imageBytes) {
        MenuItem menuItem = menuItemDao.getById(itemId).orElseThrow(() -> new RuntimeException("Invalid itemId"));
        return menuItemDao.edit(itemId, name, detail, price, sectionId, ordering, menuItem.getImageId());
    }

    @Override
    public boolean edit(long itemId, String name, String detail, double price, long sectionId, byte[] imageBytes) {
        MenuItem menuItem = menuItemDao.getById(itemId).orElseThrow(() -> new RuntimeException("Invalid itemId"));
        Long imageId = menuItem.getImageId();
        if (imageBytes.length > 0) {
            if (menuItem.getImageId() != null) {
                imageService.delete(menuItem.getImageId());
            }
            imageId = imageService.create(imageBytes).getId();
        }
        return menuItemDao.edit(itemId, name, detail, price, sectionId, menuItem.getOrdering(), imageId);
    }

    private boolean move(final long itemId, boolean moveUp) {
        MenuItem menuItem = menuItemDao.getById(itemId).orElseThrow(() -> new RuntimeException("Invalid itemId"));
        MenuSection menuSection = menuSectionService.getById(menuItem.getSectionId()).orElseThrow(IllegalStateException::new);
        Restaurant restaurant = restaurantService.getById(menuSection.getRestaurantId()).orElseThrow(IllegalStateException::new);
        if (restaurant.getUserID() != securityService.getCurrentUser().orElseThrow(UnauthenticatedUserException::new).getId())
            throw new ForbiddenActionException();
        return edit(itemId, menuItem.getName(), menuItem.getDetail(), menuItem.getPrice(), menuItem.getSectionId(), menuItem.getOrdering() + (moveUp ? -1 : 1), null);

    }

    @Override
    public boolean moveUp(final long itemId) {
        return move(itemId, true);
    }

    @Override
    public boolean moveDown(final long itemId) {
        return move(itemId, false);
    }

}
