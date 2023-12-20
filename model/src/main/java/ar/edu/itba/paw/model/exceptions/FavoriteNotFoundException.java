package ar.edu.itba.paw.model.exceptions;

public class FavoriteNotFoundException extends NotFoundException {

    private final static String MESSAGE = "Favorite not found";

    @Override
    public String getMessage() {
        return MESSAGE;
    }
}
