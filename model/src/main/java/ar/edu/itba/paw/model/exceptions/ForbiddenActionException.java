package ar.edu.itba.paw.model.exceptions;

public class ForbiddenActionException extends RuntimeException {

    private static final String MESSAGE = "You do not have the privileges to do this action";

    @Override
    public String getMessage() {
        return MESSAGE;
    }
}
