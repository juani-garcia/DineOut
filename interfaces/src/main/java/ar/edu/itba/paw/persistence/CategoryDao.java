package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.model.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryDao {

    Optional<Category> getById(long id);

    List<Category> getAll();

    List<Category> getByRestaurantId(long restaurantId);

    boolean delete(long restaurantId, long categoryId);

    boolean add(long restaurantId, long categoryId);

}
