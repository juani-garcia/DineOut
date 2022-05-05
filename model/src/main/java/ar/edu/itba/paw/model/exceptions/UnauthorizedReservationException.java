package ar.edu.itba.paw.model.exceptions;

// TODO: catch this exception in error handler
public class UnauthorizedReservationException extends RuntimeException {

    private static final String MESSAGE = "Not the owner of this reservation";

    @Override
    public String getMessage() {
        return MESSAGE;
    }
}
