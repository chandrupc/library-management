package com.library.management.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

/**
 * Data Transfer Object (DTO) for Book entities in the library management system.
 * <p>
 * This class represents the book data structure used for transferring book information
 * between the client and server. It includes validation constraints to ensure data
 * integrity and provides a clean separation between the application's domain model
 * and the representation used in API requests/responses.
 * </p>
 * <p>
 * The class uses Lombok annotations to reduce boilerplate code by automatically
 * generating getters, setters, and constructors. It also leverages Jakarta Bean
 * Validation annotations to enforce data validation rules.
 * </p>
 *
 * @author Chandru
 * @version 1.0
 * @see jakarta.validation.constraints.NotBlank
 * @see org.hibernate.validator.constraints.Length
 * @since 2025-05-19
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookDTO {

    /**
     * Unique identifier for the book.
     * <p>
     * This field represents the primary key in the database and is typically
     * auto-generated when a new book is created.
     * </p>
     */
    Long id;

    /**
     * Title of the book.
     * <p>
     * This field must not be blank and must have a length between 2 and 255 characters.
     * The title is a required field when creating or updating book records.
     * </p>
     */
    @NotBlank(message = "Title of the book is mandatory")
    @Length(min = 2, max = 255, message = "Please provide a title greater than one character and less than 255 characters")
    String title;

    /**
     * Author of the book.
     * <p>
     * This field must not be blank and must have a length between 2 and 50 characters.
     * The author name is a required field when creating or updating book records.
     * </p>
     */
    @NotBlank(message = "Author name is mandatory")
    @Length(min = 2, max = 50, message = "Please provide a title greater than one character and less than 50 characters")
    String author;

    /**
     * ISBN (International Standard Book Number) of the book.
     * <p>
     * This field must not be blank and must have a length between 3 and 50 characters.
     * The ISBN is a unique identifier for books and is required when creating or updating book records.
     * </p>
     */
    @NotBlank(message = "ISBN Number is mandatory")
    @Length(min = 3, max = 50, message = "Please provide a title greater than one character and less than 50 characters")
    String isbnNo;

    /**
     * Version of the book
     */
    Integer version;
}