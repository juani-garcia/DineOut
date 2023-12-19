package ar.edu.itba.paw.model.exceptions;

public class RestaurantNotFoundException extends NotFoundException {

    private final static String MESSAGE = "Restaurant not found";

    @Override
    public String getMessage() {
        return MESSAGE;
    }
}
