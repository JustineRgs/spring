package fr.diginamic.hello.exceptions;

/**
 * Exception thrown when a department is not found in the database.
 */
public class DepartementNotFoundException extends RuntimeException {

    /**
     * Constructs a new DepartementNotFoundException with the specified detail message.
     *
     * @param message the detail message, which is saved for later retrieval
     *                by the {@link #getMessage()} method
     */
    public DepartementNotFoundException(String message) {
        super(message);
    }
}
