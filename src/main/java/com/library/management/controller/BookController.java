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

@RestController
@RequestMapping("/book")
public class BookController {

    @Autowired
    private BookService bookService;

    @Autowired
    private LedgerService ledgerService;

    /**
     * @param book
     * @return
     * @throws Exception
     */
    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public String addBook(@Valid @RequestBody BookDTO book) throws Exception {
        bookService.addBook(book);
        return "Added new book in your Library";
    }

    /**
     * @param pageNum
     * @param pageSize
     * @return
     * @throws Exception
     */

    @GetMapping("/")
    public List<BookDTO> getBooks(@RequestParam int pageNum, @RequestParam int pageSize) throws Exception {
        PageRequest pageable = PageRequest.of(pageNum, pageSize);
        return bookService.getBooks(pageable);
    }

    /**
     * @param bookId
     * @param borrowerId
     * @return
     * @throws Exception
     */
    @PostMapping("/borrow")
    @ResponseStatus(HttpStatus.OK)
    public String borrowBook(@RequestParam Long bookId, @RequestParam Long borrowerId) throws Exception {
        ledgerService.handleLedger(bookId, borrowerId, true);
        return "Borrowed book Successfully";
    }

    /**
     * @param bookId
     * @param borrowerId
     * @return
     * @throws Exception
     */
    @PostMapping("/return")
    @ResponseStatus(HttpStatus.OK)
    public String returnBook(@RequestParam Long bookId, @RequestParam Long borrowerId) throws Exception {
        ledgerService.handleLedger(bookId, borrowerId, false);
        return "Returned book Successfully";
    }
}
