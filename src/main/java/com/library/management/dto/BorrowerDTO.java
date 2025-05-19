package com.library.management.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

/**
 * Data Transfer Object (DTO) for Borrower entities in the library management system.
 * <p>
 * This class represents the borrower data structure used for transferring borrower information
 * between the client and server. It includes validation constraints to ensure data
 * integrity and provides a clean separation between the application's domain model
 * and the representation used in API requests/responses.
 * </p>
 * <p>
 * The class uses Lombok annotations to reduce boilerplate code by automatically
 * generating getters, setters, and constructors. It also leverages Jakarta Bean
 * Validation annotations to enforce data validation rules on borrower properties.
 * </p>
 *
 * @author Chandru
 * @version 1.0
 * @see jakarta.validation.constraints.NotBlank
 * @see jakarta.validation.constraints.Email
 * @see org.hibernate.validator.constraints.Length
 * @since 2025-05-19
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BorrowerDTO {

    /**
     * Unique identifier for the borrower.
     * <p>
     * This field represents the primary key in the database and is typically
     * auto-generated when a new borrower is registered in the system.
     * </p>
     */
    Long id;

    /**
     * Name of the borrower.
     * <p>
     * This field must not be blank and must have a length between 2 and 50 characters.
     * The name is a required field when creating or updating borrower records and is
     * used for identification and display purposes throughout the library system.
     * </p>
     */
    @NotBlank(message = "Borrower name is mandatory")
    @Length(min = 2, max = 50, message = "Please provide a name greater than one character and less than 50 characters")
    String name;

    /**
     * Email address of the borrower.
     * <p>
     * This field must not be blank, must be a valid email format, and must have a length
     * between 2 and 50 characters. The email is a required field when creating or updating
     * borrower records and is used for communication purposes such as sending notifications
     * about due dates, available books, or library updates.
     * </p>
     */
    @Email
    @NotBlank(message = "Borrower email is mandatory")
    @Length(min = 2, max = 50, message = "Please provide an email greater than one character and less than 50 characters")
    String email;
}