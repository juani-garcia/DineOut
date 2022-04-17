package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryService {

    Optional<Category> getById(long id);

    List<Category> getByRestaurantId(long restaurantId);

    List<Category> getAll();

    boolean delete(long restaurantId, long categoryId);

    boolean add(long restaurantId, long categoryId);

}
