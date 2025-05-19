package com.library.management.service.impl;

import com.library.management.enums.LedgerStatus;
import com.library.management.exception.ConflictException;
import com.library.management.model.Book;
import com.library.management.model.Borrower;
import com.library.management.model.Ledger;
import com.library.management.repository.BookRepository;
import com.library.management.repository.BorrowerRepository;
import com.library.management.repository.LedgerRepository;
import com.library.management.service.LedgerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Implementation of {@link LedgerService} for handling book borrowing and returning operations.
 * <p>
 * This service manages the {@link Ledger} entries representing book transactions, ensuring that
 * rules around borrowing and returning are enforced (e.g., a book cannot be borrowed twice before it is returned).
 * </p>
 *
 * <p>
 * It verifies the existence of the {@link Book} and {@link Borrower}, checks the current borrow status,
 * and either creates a new ledger record for a borrow or updates it for a return.
 * </p>
 *
 * @author Chandru
 * @version 1.0
 * @since 2025-05-19
 */
@Service
public class LedgerServiceImpl implements LedgerService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private BorrowerRepository borrowerRepository;

    @Autowired
    private LedgerRepository ledgerRepository;

    /**
     * Handles borrowing or returning a book by creating or updating ledger entries.
     *
     * @param bookId     the ID of the book involved in the transaction
     * @param borrowerId the ID of the borrower performing the transaction
     * @param isBorrow   {@code true} to borrow the book, {@code false} to return it
     * @throws ConflictException if the operation violates borrowing rules:
     *                           <ul>
     *                             <li>Book or borrower does not exist</li>
     *                             <li>Borrowing a book that's already borrowed</li>
     *                             <li>Returning a book that hasn't been borrowed</li>
     *                           </ul>
     */
    @Override
    public void handleLedger(Long bookId, Long borrowerId, boolean isBorrow) throws ConflictException {
        Optional<Book> book = bookRepository.findById(bookId);
        if (book.isEmpty()) {
            throw new ConflictException("Book not exists to borrow");
        }

        Optional<Borrower> borrower = borrowerRepository.findById(borrowerId);
        if (borrower.isEmpty()) {
            throw new ConflictException("Borrower not exists to borrow book");
        }

        Optional<Ledger> ledger = ledgerRepository.findByBookIdAndStatus(bookId, LedgerStatus.BORROWED);

        if (isBorrow && ledger.isPresent()) {
            throw new ConflictException("Book is already borrowed by someone");
        } else if (!isBorrow && ledger.isEmpty()) {
            throw new ConflictException("Book is not borrowed to return");
        }

        if (isBorrow) {
            Ledger ledgerEntry = new Ledger();
            ledgerEntry.setBookId(bookId);
            ledgerEntry.setBorrowerId(borrowerId);
            ledgerEntry.setStatus(LedgerStatus.BORROWED);
            ledgerRepository.save(ledgerEntry);
        } else {
            Ledger ledgerEntry = ledger.get();
            ledgerEntry.setStatus(LedgerStatus.RETURNED);
            ledgerRepository.save(ledgerEntry);
        }
    }
}
