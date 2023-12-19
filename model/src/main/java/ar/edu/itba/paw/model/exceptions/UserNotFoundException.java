package ar.edu.itba.paw.model.exceptions;

public class UserNotFoundException extends NotFoundException {

    private final static String MESSAGE = "User not found";

    @Override
    public String getMessage() {
        return MESSAGE;
    }
}
