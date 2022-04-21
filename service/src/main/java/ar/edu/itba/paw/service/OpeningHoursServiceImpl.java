package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.OpeningHours;
import ar.edu.itba.paw.persistence.OpeningHoursDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OpeningHoursServiceImpl implements OpeningHoursService {

    @Autowired
    private OpeningHoursDao openingHoursDao;

    @Override
    public List<OpeningHours> getByRestaurantId(long restaurantId) {
        return openingHoursDao.getByRestaurantId(restaurantId);
    }

    @Override
    public boolean add(long restaurantId, OpeningHours openingHours) {
        return openingHoursDao.add(restaurantId, openingHours);
    }

    @Override
    public boolean delete(long restaurantId, OpeningHours openingHours) {
        return openingHoursDao.delete(restaurantId, openingHours);
    }
}
