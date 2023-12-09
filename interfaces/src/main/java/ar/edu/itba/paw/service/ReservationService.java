package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.PagedQuery;
import ar.edu.itba.paw.model.Reservation;

import java.time.LocalDateTime;
import java.util.Optional;

public interface ReservationService {

    Reservation create(long restaurantId, String userMail, int amount, LocalDateTime dateTime, String comments, String contextPath);

    Optional<Reservation> getById(final long id);

    PagedQuery<Reservation> getForUser(Long userId, int page, boolean past);

    void delete(long reservationId, String contextPath);

    void confirm(long reservationId, String contextPath);

}
