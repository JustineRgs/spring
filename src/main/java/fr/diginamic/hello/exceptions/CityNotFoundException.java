package fr.diginamic.hello.exceptions;

/**
 * Exception thrown when a city is not found in the database.
 */
public class CityNotFoundException extends RuntimeException {

    /**
     * Constructs a new CityNotFoundException with the specified detail message.
     *
     * @param message the detail message, which is saved for later retrieval
     *                by the {@link #getMessage()} method
     */
    public CityNotFoundException(String message) {
        super(message);
    }
}
