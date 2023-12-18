package ar.edu.itba.paw.model.exceptions;

public class ReviewNotFoundException extends NotFoundException {

    private final static String MESSAGE = "Review not found";

    @Override
    public String getMessage() {
        return MESSAGE;
    }
}
