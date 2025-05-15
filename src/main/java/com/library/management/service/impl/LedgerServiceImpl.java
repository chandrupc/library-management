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

@Service
public class LedgerServiceImpl implements LedgerService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private BorrowerRepository borrowerRepository;

    @Autowired
    private LedgerRepository ledgerRepository;

    /**
     * @param bookId
     * @param borrowerId
     * @param isBorrow
     * @throws ConflictException
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
            Ledger ledger1 = new Ledger();
            ledger1.setBookId(bookId);
            ledger1.setBorrowerId(borrowerId);
            ledger1.setStatus(LedgerStatus.BORROWED);
            ledgerRepository.save(ledger1);
        } else {
            Ledger ledger1 = ledger.get();
            ledger1.setStatus(LedgerStatus.RETURNED);
            ledgerRepository.save(ledger1);
        }
    }
}
