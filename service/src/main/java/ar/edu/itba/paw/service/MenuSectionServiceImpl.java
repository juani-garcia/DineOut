package ar.edu.itba.paw.service;

import ar.edu.itba.paw.persistence.MenuSection;
import ar.edu.itba.paw.persistence.MenuSectionDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuSectionServiceImpl implements MenuSectionService {

    @Autowired
    private MenuSectionDao menuSectionDao;

    @Override
    public List<MenuSection> getByRestaurantId(long restaurantId) {
        return menuSectionDao.getByRestaurantId(restaurantId);
    }

    @Override
    public MenuSection create(String name, long restaurantId, long ordering) {
        return menuSectionDao.create(name, restaurantId, ordering);
    }

    @Override
    public boolean delete(long sectionId) {
        return menuSectionDao.delete(sectionId);
    }

    @Override
    public boolean edit(long sectionId, String name, long restaurantId, long ordering) {
        return menuSectionDao.edit(sectionId, name, restaurantId, ordering);
    }
}
