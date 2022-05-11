package ar.edu.itba.paw.model.exceptions;

public class NotFoundException extends RuntimeException {

    private final static String MESSAGE = "Not found";

    @Override
    public String getMessage() {
        return MESSAGE;
    }
}
