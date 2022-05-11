package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.exceptions.MenuSectionNotFoundException;
import ar.edu.itba.paw.model.exceptions.NotFoundException;
import ar.edu.itba.paw.model.exceptions.UnauthenticatedUserException;
import ar.edu.itba.paw.persistence.MenuSection;
import ar.edu.itba.paw.persistence.MenuSectionDao;
import ar.edu.itba.paw.persistence.Restaurant;
import ar.edu.itba.paw.persistence.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MenuSectionServiceImpl implements MenuSectionService {

    @Autowired
    private SecurityService securityService;

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private MenuSectionDao menuSectionDao;

    @Autowired
    private MenuItemService menuItemService;

    @Override
    public Optional<MenuSection> getById(final long sectionId) {
        return menuSectionDao.getById(sectionId);
    }

    @Override
    public List<MenuSection> getByRestaurantId(long restaurantId) {
        List<MenuSection> menuSectionList = menuSectionDao.getByRestaurantId(restaurantId);
        menuSectionList.forEach((section) -> section.setMenuItemList(menuItemService.getBySectionId(section.getId())));  // TODO:  join in the dao.
        return menuSectionList;
    }

    @Override
    public MenuSection create(final long restaurantId, final String name) {
        return menuSectionDao.create(restaurantId, name);
    }

    @Override
    public boolean delete(final long sectionId) {
        MenuSection menuSection = getById(sectionId).orElseThrow(() -> new RuntimeException("Invalid section"));
        Restaurant restaurant = restaurantService.getById(menuSection.getRestaurantId()).orElseThrow(NotFoundException::new);
        if (restaurant.getUserID() != securityService.getCurrentUser().orElseThrow(UnauthenticatedUserException::new).getId())
            throw new RuntimeException("Invalid permissions");
        return menuSectionDao.delete(sectionId);
    }

    @Override
    public boolean edit(long sectionId, String name, long restaurantId, long ordering) {
        return menuSectionDao.edit(sectionId, name, restaurantId, ordering);
    }

    @Override
    public boolean updateName(final long sectionId, final String newName) {
        MenuSection menuSection = getById(sectionId).orElseThrow(MenuSectionNotFoundException::new);
        return edit(sectionId, newName, menuSection.getRestaurantId(), menuSection.getOrdering());
    }

    private boolean move(final long sectionId, boolean moveUp) {
        User user = securityService.getCurrentUser().orElseThrow(UnauthenticatedUserException::new);
        Restaurant restaurant = restaurantService.getByUserID(user.getId()).orElseThrow(() -> new RuntimeException("Invalid user"));
        List<MenuSection> menuSections = getByRestaurantId(restaurant.getId());
        Optional<MenuSection> optionalMenuSection = getById(sectionId);
        if (!optionalMenuSection.isPresent() || (moveUp ? optionalMenuSection.get().getOrdering() <= 1 : optionalMenuSection.get().getOrdering() >= menuSections.size()) || menuSections.stream().noneMatch((section) -> section.getId() == sectionId)) {
            throw new RuntimeException("Invalid sectionId");
        }
        MenuSection menuSection = optionalMenuSection.get();
        return edit(sectionId, menuSection.getName(), menuSection.getRestaurantId(), menuSection.getOrdering() + (moveUp ? -1 : 1));
    }

    @Override
    public boolean moveUp(final long sectionId) {
        return move(sectionId, true);
    }

    @Override
    public boolean moveDown(long sectionId) {
        return move(sectionId, false);
    }
}
