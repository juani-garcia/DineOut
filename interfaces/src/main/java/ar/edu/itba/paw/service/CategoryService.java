package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryService {

    List<Category> getByRestaurantId(long restaurantId);

    boolean delete(long restaurantId, Category category);

    boolean add(long restaurantId, Category category);

}
