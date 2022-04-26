package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.Shift;

import java.util.List;

public interface OpeningHoursService {

    List<Shift> getByRestaurantId(long restaurantId);

    boolean add(long restaurantId, Shift shift);

    boolean delete(long restaurantId, Shift shift);

}
