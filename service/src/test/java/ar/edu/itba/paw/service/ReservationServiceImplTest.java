package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.Reservation;
import ar.edu.itba.paw.model.exceptions.NotFoundException;
import ar.edu.itba.paw.persistence.ReservationDao;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static ar.edu.itba.paw.service.TestValues.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class ReservationServiceImplTest {

    @InjectMocks
    private ReservationServiceImpl reservationService;

    @Mock
    private RestaurantService restaurantService;
    @Mock
    private EmailService emailService;
    @Mock
    private SecurityService securityService;
    @Mock
    private ReservationDao reservationDao;

    @Before
    public void setUp() {
        Reservation reservation = new Reservation(ID, AMOUNT, DATETIME, COMMENTS, RESTAURANT, USER, IS_CONFIRMED);
        when(restaurantService.getById(anyLong())).
                thenReturn(Optional.of(RESTAURANT));
        when(reservationDao.create(any(), any(), anyInt(), any(), anyString())).
                thenReturn(reservation);
        when(securityService.getCurrentUser()).
                thenReturn(Optional.of(USER));
    }

    @Test
    public void testCreateReservation() {

        try {
            Reservation r = reservationService.create(RESTAURANT_ID, RESTAURANT_MAIL, AMOUNT, DATETIME, COMMENTS);
        } catch (Exception e) {
            System.out.println(e.getClass());
            Assert.fail("Unexpected error during operation create reservation: " + e.getMessage());
        }

    }

    @Test
    public void testInvalidRestaurantId() {
        when(restaurantService.getById(anyLong())).thenReturn(Optional.empty());

        Assert.assertThrows(NotFoundException.class, () -> reservationService.create(RESTAURANT_ID, RESTAURANT_MAIL, AMOUNT, DATETIME, COMMENTS));
    }

    @Test
    public void testRequireLogin() {
        when(securityService.getCurrentUser()).
                thenReturn(Optional.empty());

        Assert.assertThrows(IllegalStateException.class, () -> reservationService.create(RESTAURANT_ID, RESTAURANT_MAIL, AMOUNT, DATETIME, COMMENTS));
    }

    @Test
    public void testSendEmails() {
        //
    }

}
