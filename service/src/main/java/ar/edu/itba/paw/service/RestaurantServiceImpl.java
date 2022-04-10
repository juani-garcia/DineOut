package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.Restaurant;
import ar.edu.itba.paw.persistence.RestaurantDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RestaurantServiceImpl implements RestaurantService {

    @Autowired
    private RestaurantDao restaurantDao;

    @Override
    public Optional<Restaurant> getRestaurantById(long id) {
        return restaurantDao.getRestaurantById(id);
    }

    @Override
    public List<Restaurant> getAll(int page) {
        return restaurantDao.getAll(page);
    }
}
