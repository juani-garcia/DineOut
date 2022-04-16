package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.MenuSection;
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
}
