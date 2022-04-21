package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.OpeningHours;

import java.util.List;

public interface OpeningHoursService {

    List<OpeningHours> getByRestaurantId(long restaurantId);

    boolean add(long restaurantId, OpeningHours openingHours);

    boolean delete(long restaurantId, OpeningHours openingHours);

}
