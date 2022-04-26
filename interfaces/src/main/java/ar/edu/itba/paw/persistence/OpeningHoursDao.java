package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.model.Shift;

import java.util.List;

public interface OpeningHoursDao {

    List<Shift> getByRestaurantId(long restaurantId);

    boolean add(long restaurantId, Shift shift);

    boolean delete(long restaurantId, Shift shift);
}
