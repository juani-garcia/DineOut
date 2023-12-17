package ar.edu.itba.paw.model.exceptions;

public class RepeatedReviewException extends IllegalArgumentException {

    private static final String MESSAGE = "You are trying to add a second review to a restaurant";

    @Override
    public String getMessage() {
        return MESSAGE;
    }
}
