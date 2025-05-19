package com.library.management.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Data Transfer Object (DTO) for standardized error responses in the library management system.
 * <p>
 * This class provides a consistent structure for error information returned to clients
 * when exceptions occur during API requests. It encapsulates details such as the timestamp
 * when the error occurred, one or more error messages, additional details about the context,
 * and an optional error code.
 * </p>
 * <p>
 * ErrorDetailsDTO is used by the global exception handling system to create uniform
 * error responses across the application, improving client experience and debugging.
 * </p>
 *
 * @author Chandru
 * @version 1.0
 * @see com.library.management.config.ExceptionAdvice
 * @since 2025-05-19
 */
public class ErrorDetailsDTO {
    /**
     * The timestamp when the error occurred.
     * <p>
     * This field helps in tracking when specific errors happened for logging
     * and troubleshooting purposes.
     * </p>
     */
    private Date timestamp;

    /**
     * List of error messages associated with this error.
     * <p>
     * This field can contain multiple error messages, which is particularly useful
     * for validation errors where multiple fields might have failed validation.
     * Initialized as an empty ArrayList to prevent null pointer exceptions.
     * </p>
     */
    private List<String> messages = new ArrayList<>();

    /**
     * Additional details about the error context.
     * <p>
     * This typically includes information about the request that caused the error,
     * such as the URI or other contextual information useful for debugging.
     * </p>
     */
    private String details;

    /**
     * Optional error code for categorizing errors.
     * <p>
     * This field can be used to assign specific error codes to different types of errors,
     * making it easier for clients to handle them programmatically.
     * </p>
     */
    private String errorCode;

    /**
     * Gets the error code.
     *
     * @return the error code associated with this error
     */
    public String getErrorCode() {
        return errorCode;
    }

    /**
     * Sets the error code.
     *
     * @param errorCode the error code to set
     */
    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    /**
     * Constructor for creating an error details object with a single error message.
     *
     * @param timestamp the time when the error occurred
     * @param message   a single error message
     * @param details   additional context about the error
     */
    public ErrorDetailsDTO(Date timestamp, String message, String details) {
        super();
        this.timestamp = timestamp;
        this.messages.add(message);
        this.details = details;
    }

    /**
     * Constructor for creating an error details object with multiple error messages.
     * <p>
     * This is particularly useful for validation errors where multiple fields
     * might have failed validation simultaneously.
     * </p>
     *
     * @param timestamp the time when the error occurred
     * @param message   a list of error messages
     * @param details   additional context about the error
     */
    public ErrorDetailsDTO(Date timestamp, List<String> message, String details) {
        super();
        this.timestamp = timestamp;
        this.messages = message;
        this.details = details;
    }

    /**
     * Gets the timestamp when the error occurred.
     *
     * @return the timestamp of the error
     */
    public Date getTimestamp() {
        return timestamp;
    }

    /**
     * Gets the list of error messages.
     *
     * @return the list of error messages associated with this error
     */
    public List<String> getMessage() {
        return messages;
    }

    /**
     * Gets the additional details about the error context.
     *
     * @return the details string providing context about the error
     */
    public String getDetails() {
        return details;
    }
}