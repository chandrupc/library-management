package com.library.management.controller;

import com.library.management.dto.BookDTO;
import com.library.management.service.BookService;
import com.library.management.service.LedgerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller for managing book-related operations in the library system.
 * <p>
 * This controller handles HTTP requests related to book management including
 * adding new books, retrieving book listings, and managing the borrowing and
 * returning process. It exposes RESTful endpoints that respond to various HTTP
 * methods for client applications to interact with the library's book collection.
 * </p>
 *
 * @author Chandru
 * @version 1.0
 * @see BookService
 * @see LedgerService
 * @see BookDTO
 * @since 2025-05-19
 */
@RestController
@RequestMapping("/book")
public class BookController {

    @Autowired
    private BookService bookService;

    @Autowired
    private LedgerService ledgerService;

    /**
     * Adds a new book to the library collection.
     * <p>
     * This endpoint validates the incoming book data and stores it in the library's
     * database. The book information is expected to be provided as JSON in the request body.
     * Validation errors will trigger a BAD_REQUEST response with details about the validation failures.
     * </p>
     *
     * @param book The book data transfer object containing details of the book to be added
     * @return A confirmation message indicating the book was successfully added
     * @throws Exception If a book with the same identifier already exists or other processing errors occur
     */
    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public String addBook(@Valid @RequestBody BookDTO book) throws Exception {
        bookService.addBook(book);
        return "Added new book in your Library";
    }

    /**
     * Retrieves a paginated list of books from the library collection.
     * <p>
     * This endpoint returns books in pages based on the specified page number and size,
     * allowing clients to efficiently navigate through large collections of books.
     * </p>
     *
     * @param pageNum  The zero-based page number to retrieve
     * @param pageSize The number of books to include in each page
     * @return A list of books for the requested page
     * @throws Exception If there are errors accessing the book repository or processing the request
     */
    @GetMapping("/")
    public List<BookDTO> getBooks(@RequestParam int pageNum, @RequestParam int pageSize) throws Exception {
        PageRequest pageable = PageRequest.of(pageNum, pageSize);
        return bookService.getBooks(pageable);
    }

    /**
     * Processes a book borrowing request.
     * <p>
     * This endpoint records a borrowing transaction in the library's ledger,
     * marking the specified book as borrowed by the identified borrower.
     * The book's availability status is updated accordingly.
     * </p>
     *
     * @param bookId     The unique identifier of the book to be borrowed
     * @param borrowerId The unique identifier of the user borrowing the book
     * @return A confirmation message indicating successful borrowing
     * @throws Exception If the book is not available, the borrower is not eligible,
     *                   or other processing errors occur
     */
    @PostMapping("/borrow")
    @ResponseStatus(HttpStatus.OK)
    public String borrowBook(@RequestParam Long bookId, @RequestParam Long borrowerId) throws Exception {
        ledgerService.handleLedger(bookId, borrowerId, true);
        return "Borrowed book Successfully";
    }

    /**
     * Processes a book return request.
     * <p>
     * This endpoint records a return transaction in the library's ledger,
     * marking the specified book as returned by the identified borrower.
     * The book's availability status is updated accordingly.
     * </p>
     *
     * @param bookId     The unique identifier of the book being returned
     * @param borrowerId The unique identifier of the user returning the book
     * @return A confirmation message indicating successful return
     * @throws Exception If the book was not borrowed by the specified user
     *                   or other processing errors occur
     */
    @PostMapping("/return")
    @ResponseStatus(HttpStatus.OK)
    public String returnBook(@RequestParam Long bookId, @RequestParam Long borrowerId) throws Exception {
        ledgerService.handleLedger(bookId, borrowerId, false);
        return "Returned book Successfully";
    }
}