package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.Category;

import java.util.List;

public interface CategoryService {

    List<Category> getByRestaurantId(long restaurantId);

    boolean delete(long restaurantId, Category category);

    boolean add(long restaurantId, Category category);

}
