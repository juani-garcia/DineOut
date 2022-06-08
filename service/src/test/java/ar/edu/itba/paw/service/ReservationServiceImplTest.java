package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.exceptions.NotFoundException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static ar.edu.itba.paw.service.TestValues.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class ReservationServiceImplTest {

    @InjectMocks
    private ReservationServiceImpl reservationService;

    @Mock
    private RestaurantService restaurantService;
    @Mock
    private SecurityService securityService;

    @Before
    public void setUp() {
        when(restaurantService.getById(anyLong())).
                thenReturn(Optional.of(RESTAURANT));
        when(securityService.getCurrentUser()).
                thenReturn(Optional.of(USER));
    }

    @Test
    public void testInvalidRestaurantId() {
        when(restaurantService.getById(anyLong())).thenReturn(Optional.empty());

        Assert.assertThrows(NotFoundException.class, () -> reservationService.create(RESTAURANT_ID, RESTAURANT_MAIL, RESERVATION_AMOUNT, RESERVATION_DATETIME, RESERVATION_COMMENTS, null));
    }

    @Test
    public void testRequireLogin() {
        when(securityService.getCurrentUser()).
                thenReturn(Optional.empty());

        Assert.assertThrows(IllegalStateException.class, () -> reservationService.create(RESTAURANT_ID, RESTAURANT_MAIL, RESERVATION_AMOUNT, RESERVATION_DATETIME, RESERVATION_COMMENTS, null));
    }

    @Test
    public void testSendEmails() {
        //
    }

}
