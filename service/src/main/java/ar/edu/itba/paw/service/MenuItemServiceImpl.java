package ar.edu.itba.paw.service;

import ar.edu.itba.paw.persistence.*;
import ar.edu.itba.paw.persistence.Image;
import ar.edu.itba.paw.persistence.MenuItem;
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
        MenuItem menuItem = getById(itemId).orElseThrow( () -> new RuntimeException("Invalid itemId"));  // TODO: Modularizar
        MenuSection menuSection = menuSectionService.getById(menuItem.getSectionId()).orElseThrow(IllegalStateException::new);
        Restaurant restaurant = restaurantService.getById(menuSection.getRestaurantId()).orElseThrow(IllegalStateException::new);
        if (restaurant.getUserID() != securityService.getCurrentUser().orElseThrow(IllegalStateException::new).getId())
            throw new RuntimeException("Invalid permissions");
        return menuItemDao.delete(itemId);
    }

    public boolean edit(long itemId, String name, String detail, double price, long sectionId, long ordering, byte[] imageBytes) {
        MenuItem menuItem = menuItemDao.getById(itemId).orElseThrow( () -> new RuntimeException("Invalid itemId"));
        return menuItemDao.edit(itemId, name, detail, price, sectionId, ordering, menuItem.getImageId());
    }

    @Override
    public boolean edit(long itemId, String name, String detail, double price, long sectionId, byte[] imageBytes) {
        MenuItem menuItem = menuItemDao.getById(itemId).orElseThrow( () -> new RuntimeException("Invalid itemId"));
        return menuItemDao.edit(itemId, name, detail, price, sectionId, menuItem.getOrdering(), menuItem.getImageId());
    }

    @Override
    public boolean moveUp(final long itemId) {
        MenuItem menuItem = menuItemDao.getById(itemId).orElseThrow( () -> new RuntimeException("Invalid itemId"));  // TODO: Modularizar
        MenuSection menuSection = menuSectionService.getById(menuItem.getSectionId()).orElseThrow(IllegalStateException::new);
        Restaurant restaurant = restaurantService.getById(menuSection.getRestaurantId()).orElseThrow(IllegalStateException::new);
        if (restaurant.getUserID() != securityService.getCurrentUser().orElseThrow(IllegalStateException::new).getId())
            throw new RuntimeException("This user does not have access");
        return edit(itemId, menuItem.getName(), menuItem.getDetail(), menuItem.getPrice(), menuItem.getSectionId(), menuItem.getOrdering() - 1, null);
    }

    @Override
    public boolean moveDown(final long itemId) {
        MenuItem menuItem = menuItemDao.getById(itemId).orElseThrow( () -> new RuntimeException("Invalid itemId"));  // TODO: Modularizar
        MenuSection menuSection = menuSectionService.getById(menuItem.getSectionId()).orElseThrow(IllegalStateException::new);
        Restaurant restaurant = restaurantService.getById(menuSection.getRestaurantId()).orElseThrow(IllegalStateException::new);
        if (restaurant.getUserID() != securityService.getCurrentUser().orElseThrow(IllegalStateException::new).getId())
            throw new RuntimeException("This user does not have access");
        return edit(itemId, menuItem.getName(), menuItem.getDetail(), menuItem.getPrice(), menuItem.getSectionId(), menuItem.getOrdering() + 1, null);
    }

}
