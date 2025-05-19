package com.library.management.service;

import com.library.management.dto.BookDTO;
import com.library.management.exception.ConflictException;
import org.springframework.data.domain.PageRequest;

import java.util.List;

/**
 * Service interface for managing book-related operations in the library management system.
 * <p>
 * This interface defines the contract for adding new books and retrieving paginated lists of books.
 * </p>
 *
 * @author Chandru
 * @version 1.0
 * @since 2025-05-19
 */
public interface BookService {

    /**
     * Adds a new book to the system.
     *
     * @param book the {@link BookDTO} containing book details to be added
     * @throws Exception if the book already exists or an unexpected error occurs
     */
    void addBook(BookDTO book) throws Exception;

    /**
     * Retrieves a paginated list of books.
     *
     * @param pageable the {@link PageRequest} object defining page size and number
     * @return a list of {@link BookDTO} representing the books
     */
    List<BookDTO> getBooks(PageRequest pageable);
}
