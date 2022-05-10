package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.Category;
import ar.edu.itba.paw.persistence.CategoryDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryDao categoryDao;


    @Override
    public List<Category> getByRestaurantId(long restaurantId) {
        return categoryDao.getByRestaurantId(restaurantId);
    }

    @Override
    public boolean delete(long restaurantId, Category category) {
        return categoryDao.delete(restaurantId, category);
    }

    @Override
    public boolean add(long restaurantId, Category category) {
        return categoryDao.add(restaurantId, category);
    }
}
