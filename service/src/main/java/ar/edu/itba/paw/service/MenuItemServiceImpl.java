package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.*;
import ar.edu.itba.paw.model.exceptions.UnauthenticatedUserException;
import ar.edu.itba.paw.persistence.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        User user = securityService.getCurrentUser().orElseThrow(UnauthenticatedUserException::new);
        MenuSection menuSection = menuSectionService.getById(sectionId).orElseThrow(IllegalArgumentException::new);
        Restaurant restaurant = menuSection.getRestaurant();
        if (!Objects.equals(user.getId(), restaurant.getUser().getId()))
            throw new IllegalArgumentException("Cannot create item in someone else's restaurant");
        Image image = null;
        if (imageBytes != null && imageBytes.length > 0) {
            image = imageService.create(imageBytes);
        }
        return menuItemDao.create(name, detail, price, sectionId, (image != null) ? image.getId() : null);
    }

    @Override
    public boolean delete(final long itemId) {
        MenuItem menuItem = validateItem(itemId);
        return menuItemDao.delete(itemId);
    }

    boolean edit(long itemId, String name, String detail, double price, long sectionId, long ordering, byte[] imageBytes) {
        MenuItem menuItem = validateItem(itemId);
        return menuItemDao.edit(itemId, name, detail, price, sectionId, ordering, menuItem.getImageId());
    }

    @Transactional
    @Override
    public boolean edit(long itemId, String name, String detail, double price, long sectionId, byte[] imageBytes) {
        MenuItem menuItem = validateItem(itemId);
        Long imageId = menuItem.getImageId();
        if (imageBytes.length > 0) {
            if (menuItem.getImageId() != null) {
                imageService.delete(menuItem.getImageId());
            }
            imageId = imageService.create(imageBytes).getId();
        }
        return menuItemDao.edit(itemId, name, detail, price, sectionId, menuItem.getOrdering(), menuItem.getImageId());
    }

    private boolean move(final long itemId, boolean moveUp) {
        MenuItem menuItem = validateItem(itemId);
        return edit(itemId, menuItem.getName(), menuItem.getDetail(), menuItem.getPrice(), menuItem.getSection().getId(), menuItem.getOrdering() + (moveUp ? -1 : 1), null);

    }

    @Override
    public boolean moveUp(final long itemId) {
        return move(itemId, true);
    }

    @Override
    public boolean moveDown(final long itemId) {
        return move(itemId, false);
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

}
