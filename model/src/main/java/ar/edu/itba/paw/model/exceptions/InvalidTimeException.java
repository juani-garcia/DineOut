package ar.edu.itba.paw.model.exceptions;

public class InvalidTimeException extends IllegalArgumentException {

    private final static String MESSAGE = "Time for this reservation is invalid";

    @Override
    public String getMessage() {
        return MESSAGE;
    }

}
