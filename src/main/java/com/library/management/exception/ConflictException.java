package com.library.management.exception;

/**
 * Exception thrown when a conflict occurs in the application,
 * such as attempting to create a resource that already exists.
 * <p>
 * This exception typically results in an HTTP 409 Conflict status code
 * in web-based applications.
 * </p>
 *
 * @author Chandru
 * @version 1.0
 * @since 2025-05-19
 */
public class ConflictException extends Exception {

    private static final long serialVersionUID = 1L;

    /**
     * Constructs a new ConflictException with the specified detail message.
     *
     * @param message the detail message explaining the reason for the exception
     */
    public ConflictException(String message) {
        super(message);
    }
}
