package ar.edu.itba.paw.model.exceptions;

public class InvalidPageException extends RuntimeException {
    private static final String MESSAGE = "The page requested is invalid";

    @Override
    public String getMessage() {
        return MESSAGE;
    }
}
