package com.library.management.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * Entity representing a book in the library management system.
 * <p>
 * This class extends {@link AuditColumns} to automatically capture creation and update timestamps.
 * </p>
 *
 * <p>
 * Each book has a unique identifier, ISBN number, title, and author.
 * </p>
 *
 * @author Chandru
 * @version 1.0
 * @since 2025-05-19
 */
@Entity
@Table(name = "book")
@Getter
@Setter
public class Book extends AuditColumns {

    /**
     * Unique identifier for the book.
     * This value is auto-generated using a sequence.
     */
    @Id
    @GeneratedValue(generator = "book_seq", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "book_seq", sequenceName = "book_seq", allocationSize = 1)
    Long id;

    /**
     * International Standard Book Number (ISBN) of the book.
     */
    @Column(name = "isbn_no", length = 50)
    String isbnNo;

    /**
     * Title of the book.
     */
    @Column(name = "title", length = 255)
    String title;

    /**
     * Author of the book.
     */
    @Column(name = "author", length = 50)
    String author;

    /**
     * Version of the book
     */
    @Column(name = "version", nullable = false)
    Integer version;
}
