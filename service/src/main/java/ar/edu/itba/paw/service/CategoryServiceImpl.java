package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.Category;
import ar.edu.itba.paw.persistence.CategoryDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryDao categoryDao;

    @Override
    public Optional<Category> getById(long id) {
        return categoryDao.getById(id);
    }

    @Override
    public List<Category> getByRestaurantId(long restaurantId) {
        return categoryDao.getByRestaurantId(restaurantId);
    }

    @Override
    public List<Category> getAll() {
        return categoryDao.getAll();
    }

    @Override
    public boolean delete(long restaurantId, long categoryId) {
        return categoryDao.delete(restaurantId, categoryId);
    }

    @Override
    public boolean add(long restaurantId, long categoryId) {
        return categoryDao.add(restaurantId, categoryId);
    }
}
