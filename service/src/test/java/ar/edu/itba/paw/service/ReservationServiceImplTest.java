package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.Reservation;
import ar.edu.itba.paw.persistence.ReservationDao;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class ReservationServiceImplTest {

    private static final long ID = 1;
    private static final long RESTAURANT_ID = 1;
    private static final String MAIL = "user@mail.com";
    private static final int AMOUNT = 1;
    private static final LocalDateTime DATETIME = LocalDateTime.now();
    private static final String COMMENTS = "";

    @InjectMocks
    private ReservationServiceImpl reservationService;

    @Mock
    private ReservationDao reservationDao;

    @Test
    public void testCreateReservation() {
//        // 1. Precondiciones
//        Reservation reservation = new Reservation(ID, RESTAURANT_ID, MAIL, AMOUNT, DATETIME, COMMENTS);
//        when(reservationDao.create(anyLong(), anyString(), anyInt(), any(LocalDateTime.class), anyString())).thenReturn(reservation);
//
//        // 2. Ejercitar
//        try {
//            Reservation r = reservationService.create(RESTAURANT_ID, MAIL, AMOUNT, DATETIME, COMMENTS);
//        } catch (Exception e) {
//            System.out.println(e.getClass());
//            Assert.fail("Unexpected error during operation create reservation" + e.getMessage());
//        }

        // 3. Postcondiciones
    }

}
