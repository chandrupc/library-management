package com.library.management.service.impl;

import com.library.management.dto.BookDTO;
import com.library.management.exception.ConflictException;
import com.library.management.model.Book;
import com.library.management.repository.BookRepository;
import com.library.management.service.BookService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementation of {@link BookService} for managing book-related operations.
 * <p>
 * This service handles the logic for adding new books and retrieving paginated lists of books.
 * It uses a {@link ModelMapper} to convert between entities and DTOs.
 * </p>
 *
 * <p>
 * Includes checks for duplicate books before saving to prevent conflicts.
 * </p>
 *
 * @author Chandru
 * @version 1.0
 * @since 2025-05-19
 */
@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private ModelMapper modelMapper;

    /**
     * Adds a new copy of a book to the system.
     * <p>
     * This method allows multiple copies of the same book (identified by ISBN, title, and author)
     * to be stored by incrementing the {@code version} field for each new copy.
     * </p>
     * <p>
     * The method enforces that all copies with the same ISBN must have the same title and author.
     * If a book with the same ISBN but differing title or author is provided, a
     * {@link ConflictException} is thrown.
     * </p>
     *
     * @param bookPayload the {@link BookDTO} containing book details
     * @throws ConflictException if a book with the same ISBN exists but has a different title or author
     */
    @Override
    public void addBook(BookDTO bookPayload) throws Exception {
        // Fetch all books with the same ISBN
        List<Book> booksWithIsbn = bookRepository.findByIsbnNo(bookPayload.getIsbnNo());

        if (!booksWithIsbn.isEmpty()) {
            // Validate that all existing copies have the same title and author as the new book
            for (Book existingBook : booksWithIsbn) {
                if (!existingBook.getTitle().equals(bookPayload.getTitle()) ||
                        !existingBook.getAuthor().equals(bookPayload.getAuthor())) {
                    throw new ConflictException("Book with same ISBN must have same title and author");
                }
            }
            // Determine the highest version number among existing copies and increment it
            int maxVersion = booksWithIsbn.stream()
                    .mapToInt(Book::getVersion)
                    .max()
                    .orElse(0);

            // Map DTO to entity and assign next version number
            Book newBook = modelMapper.map(bookPayload, Book.class);
            newBook.setVersion(maxVersion + 1);
            bookRepository.save(newBook);
        } else {
            // No existing copies with this ISBN, set version to 1 for the first copy
            Book newBook = modelMapper.map(bookPayload, Book.class);
            newBook.setVersion(1);
            bookRepository.save(newBook);
        }
    }


    /**
     * Retrieves a paginated list of books from the system.
     *
     * @param pageable the {@link PageRequest} object defining page size and number
     * @return a list of {@link BookDTO} representing the books
     */
    @Override
    public List<BookDTO> getBooks(PageRequest pageable) {
        Page<Book> books = bookRepository.findAll(pageable);
        return books.stream()
                .map(e -> modelMapper.map(e, BookDTO.class))
                .collect(Collectors.toList());
    }
}
