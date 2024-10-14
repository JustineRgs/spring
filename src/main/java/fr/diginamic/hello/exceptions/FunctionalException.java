package fr.diginamic.hello.exceptions;

/**
 * Exception thrown when a functional error occurs in the application.
 */
public class FunctionalException extends Exception {

    /**
     * Constructs a new FunctionalException with the specified detail message.
     *
     * @param message the detail message, which is saved for later retrieval
     *                by the {@link #getMessage()} method
     */
    public FunctionalException(String message) {
        super(message);
    }
}
