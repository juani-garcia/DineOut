package ar.edu.itba.paw.webapp.exceptions;

public class RestaurantNotFoundException extends RuntimeException{

    private final static String MESSAGE = "Restaurant not found";

    @Override
    public String getMessage() {
        return MESSAGE;
    }
}
