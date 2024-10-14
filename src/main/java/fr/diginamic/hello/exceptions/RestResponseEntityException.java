package fr.diginamic.hello.exceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Global exception handler for REST API exceptions.
 */
@ControllerAdvice
public class RestResponseEntityException {

    /**
     * Handles FunctionalException thrown from any controller.
     *
     * @param ex the FunctionalException to handle
     * @return a ResponseEntity with a bad request status and the exception message
     */
    @ExceptionHandler({FunctionalException.class})
    protected ResponseEntity<String> handleFunctionalException(FunctionalException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }
}

