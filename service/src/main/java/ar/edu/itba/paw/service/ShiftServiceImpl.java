package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.Shift;
import ar.edu.itba.paw.persistence.ShiftDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShiftServiceImpl implements ShiftService {

    @Autowired
    private ShiftDao shiftDao;

    @Override
    public List<Shift> getByRestaurantId(long restaurantId) {
        return shiftDao.getByRestaurantId(restaurantId);
    }

    @Override
    public boolean add(long restaurantId, Shift shift) {
        return shiftDao.add(restaurantId, shift);
    }

    @Override
    public boolean delete(long restaurantId, Shift shift) {
        return shiftDao.delete(restaurantId, shift);
    }
}
