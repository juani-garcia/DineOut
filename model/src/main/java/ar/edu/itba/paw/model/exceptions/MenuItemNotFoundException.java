package ar.edu.itba.paw.model.exceptions;

public class MenuItemNotFoundException extends NotFoundException {

    private final static String MESSAGE = "Menu Item not found";

    @Override
    public String getMessage() {
        return MESSAGE;
    }
}
