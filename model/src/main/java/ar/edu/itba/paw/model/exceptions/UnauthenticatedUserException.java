package ar.edu.itba.paw.model.exceptions;

public class UnauthenticatedUserException extends RuntimeException{

    private static final String MESSAGE = "You must must authenticate itself to get the requested response.";

    @Override
    public String getMessage() {
        return MESSAGE;
    }
}
