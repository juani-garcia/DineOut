package ar.edu.itba.paw.model.exceptions;

public class MenuSectionNotFoundException extends NotFoundException {

    private final static String MESSAGE = "Menu Section not found";

    @Override
    public String getMessage() {
        return MESSAGE;
    }
}
