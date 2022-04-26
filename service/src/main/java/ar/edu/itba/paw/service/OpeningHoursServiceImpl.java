package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.Shift;
import ar.edu.itba.paw.persistence.OpeningHoursDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OpeningHoursServiceImpl implements OpeningHoursService {

    @Autowired
    private OpeningHoursDao openingHoursDao;

    @Override
    public List<Shift> getByRestaurantId(long restaurantId) {
        return openingHoursDao.getByRestaurantId(restaurantId);
    }

    @Override
    public boolean add(long restaurantId, Shift shift) {
        return openingHoursDao.add(restaurantId, shift);
    }

    @Override
    public boolean delete(long restaurantId, Shift shift) {
        return openingHoursDao.delete(restaurantId, shift);
    }
}
