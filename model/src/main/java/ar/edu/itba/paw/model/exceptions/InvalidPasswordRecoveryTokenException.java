package ar.edu.itba.paw.model.exceptions;

public class InvalidPasswordRecoveryTokenException extends IllegalArgumentException {
    private static final String MESSAGE = "Invalid password recovery token";

    @Override
    public String getMessage() {
        return MESSAGE;
    }
}
