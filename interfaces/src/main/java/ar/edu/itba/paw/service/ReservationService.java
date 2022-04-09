package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.Reservation;

import java.time.LocalDateTime;

public interface ReservationService {

    Reservation createReservation(long restaurantId, String userMail, int amount, LocalDateTime dateTime, String comments);

}