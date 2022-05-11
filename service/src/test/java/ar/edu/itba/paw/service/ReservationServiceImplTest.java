package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.Zone;
import ar.edu.itba.paw.model.exceptions.NotFoundException;
import ar.edu.itba.paw.model.Reservation;
import ar.edu.itba.paw.persistence.ReservationDao;
import ar.edu.itba.paw.model.Restaurant;
import ar.edu.itba.paw.model.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class ReservationServiceImplTest {

    private static final long ID = 1;
    private static final long RESTAURANT_ID = 1;
    private static final String USER_MAIL = "user@mail.com";
    private static final int AMOUNT = 1;
    private static final LocalDateTime DATETIME = LocalDateTime.now();
    private static final String COMMENTS = "";
    private static final boolean IS_CONFIRMED = false;

    private static final long RESTAURANT_USER_ID = 1;
    private static final String RESTAURANT_NAME = "Restaurant";
    private static final long RESTAURANT_IMAGE_ID = 1;
    private static final String RESTAURANT_ADDRESS = "Address";
    private static final String RESTAURANT_MAIL = "restaurant@mail.com";
    private static final String RESTAURANT_DETAIL = "Detail";
    private static final Zone RESTAURANT_ZONE = Zone.ACASSUSO;
    private static final long RESTAURANT_FAV_COUNT = 0;
    private static final Restaurant RESTAURANT = new Restaurant(RESTAURANT_ID,
            RESTAURANT_USER_ID, RESTAURANT_NAME, RESTAURANT_IMAGE_ID, RESTAURANT_ADDRESS, RESTAURANT_MAIL,
            RESTAURANT_DETAIL, RESTAURANT_ZONE, RESTAURANT_FAV_COUNT);

    private static final long USER_ID = 1;
    private static final String USER_USERNAME = "user@mail.com";
    private static final String USER_PASSWORD = "1234567890User";
    private static final String USER_FIRST_NAME = "John";
    private static final String USER_LAST_NAME = "Doe";
    private static final User USER = new User(USER_ID, USER_USERNAME,
            USER_PASSWORD, USER_FIRST_NAME, USER_LAST_NAME);

    @InjectMocks
    private ReservationServiceImpl reservationService;

    @Mock
    private RestaurantService restaurantService;
    @Mock
    private EmailService emailService;
    @Mock
    private ShiftService shiftService;
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
        when(securityService.getCurrentUsername()).
                thenReturn(USER_FIRST_NAME);
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
        when(securityService.getCurrentUsername()).
                thenReturn(null);

        Assert.assertThrows(IllegalStateException.class, () -> reservationService.create(RESTAURANT_ID, RESTAURANT_MAIL, AMOUNT, DATETIME, COMMENTS));
    }

    @Test
    public void testSendEmails() {
        //
    }

}
