package ar.edu.itba.paw.model.exceptions;

public class ImageIOException extends IllegalArgumentException {

    private static final String MESSAGE = "Couldn't handle IO for image and got %s";

    public ImageIOException(String message) {
        super(String.format(MESSAGE, message));
    }
}
