package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.Shift;
import ar.edu.itba.paw.persistence.Reservation;
import ar.edu.itba.paw.persistence.ReservationDao;
import ar.edu.itba.paw.model.exceptions.InvalidTimeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
public class ReservationServiceImpl implements ReservationService {

    @Autowired
    private ReservationDao reservationDao;

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private ShiftService shiftService;

    @Override
    public Reservation create(long restaurantId, String userMail, int amount, LocalDateTime dateTime, String comments) {
        if(!restaurantService.getById(restaurantId).isPresent()) {
            throw new InvalidTimeException();
        }

        if(!Shift.belongs(shiftService.getByRestaurantId(restaurantId), LocalTime.from(dateTime))) {
            throw new InvalidTimeException();
        }

        Reservation reservation = reservationDao.create(restaurantId, userMail, amount, dateTime, comments);

        String restaurantMail =  restaurantService.getById(restaurantId).orElseThrow(RuntimeException::new).getMail();

        emailService.sendReservationToRestaurant(
                reservation.getReservationId(), restaurantMail, userMail, amount, dateTime, comments);

        return reservation;
    }

    @Override
    public List<Reservation> getAllByUsername(String username) {
        return reservationDao.getAllByUsername(username);
    }
}
