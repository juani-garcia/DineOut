package ar.edu.itba.paw.model.exceptions;

public class UnauthorizedException extends RuntimeException {

    private static final String MESSAGE = "You are not logged in";

    @Override
    public String getMessage() {
        return MESSAGE;
    }

}