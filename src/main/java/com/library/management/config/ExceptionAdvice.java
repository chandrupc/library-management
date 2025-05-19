package com.library.management.config;

import com.library.management.dto.ErrorDetailsDTO;
import com.library.management.exception.ConflictException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Global exception handler for the library management system.
 * <p>
 * This class centralizes exception handling across all controllers in the application,
 * providing consistent error responses to clients. It converts various exceptions into
 * appropriate HTTP responses with structured error details.
 * </p>
 * <p>
 * Using {@link ControllerAdvice}, this class intercepts exceptions thrown from controllers
 * and transforms them into standardized error responses with relevant HTTP status codes.
 * </p>
 *
 * @author Chandru
 * @version 1.0
 * @see ErrorDetailsDTO
 * @see ConflictException
 * @since 2025-05-19
 */
@ControllerAdvice
public class ExceptionAdvice {

    /**
     * Handles {@link ConflictException} which occurs when attempting operations that
     * would create data conflicts (e.g., adding a book that already exists).
     * <p>
     * This handler returns a 409 CONFLICT response with details about the conflict.
     * </p>
     *
     * @param ex      The ConflictException that was thrown
     * @param request The current web request
     * @return ResponseEntity containing error details and HTTP status 409 CONFLICT
     */
    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<?> handleConflicts(Exception ex, WebRequest request) {
        ErrorDetailsDTO errorDetails = new ErrorDetailsDTO(new Date(), ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.CONFLICT);
    }

    /**
     * Handles validation failures for request bodies annotated with {@code @Valid}.
     * <p>
     * When request validation fails, this handler collects all field-level validation errors
     * and returns them as a consolidated error response with HTTP status 400 BAD REQUEST.
     * The response includes a list of all validation error messages to help clients correct
     * their request.
     * </p>
     *
     * @param ex      The MethodArgumentNotValidException containing validation errors
     * @param request The current web request
     * @return ResponseEntity containing all validation error messages and HTTP status 400 BAD REQUEST
     */
    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> methodArgumentNotValidException(MethodArgumentNotValidException ex, WebRequest request) {
        BindingResult result = ex.getBindingResult();
        List<FieldError> fieldErrors = result.getFieldErrors();
        List<String> errorMessages = fieldErrors.stream().map(FieldError::getDefaultMessage)
                .collect(Collectors.toList());
        ErrorDetailsDTO errorDetails = new ErrorDetailsDTO(new Date(), errorMessages, request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }
}