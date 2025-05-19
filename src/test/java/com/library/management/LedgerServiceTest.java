package com.library.management;

import com.library.management.enums.LedgerStatus;
import com.library.management.exception.ConflictException;
import com.library.management.model.Book;
import com.library.management.model.Borrower;
import com.library.management.model.Ledger;
import com.library.management.repository.BookRepository;
import com.library.management.repository.BorrowerRepository;
import com.library.management.repository.LedgerRepository;
import com.library.management.service.impl.LedgerServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

/**
 * Unit tests for {@link LedgerServiceImpl}, ensuring that book borrowing and returning logic behaves correctly.
 * <p>
 * These tests validate correct status transitions, entity existence checks, and exception handling.
 * </p>
 *
 * @author Chandru
 * @version 1.0
 * @since 2025-05-19
 */
@ExtendWith(MockitoExtension.class)
public class LedgerServiceTest {

    @Mock
    private BookRepository bookRepository;

    @Mock
    private BorrowerRepository borrowerRepository;

    @Mock
    private LedgerRepository ledgerRepository;

    @InjectMocks
    private LedgerServiceImpl ledgerService;

    private Book book;
    private Borrower borrower;
    private Ledger ledger;

    @BeforeEach
    void setUp() {
        book = new Book();
        book.setId(1L);

        borrower = new Borrower();
        borrower.setId(1L);

        ledger = new Ledger();
        ledger.setBookId(1L);
        ledger.setBorrowerId(1L);
        ledger.setStatus(LedgerStatus.BORROWED);
    }

    /**
     * Should successfully create a borrow ledger entry when the book is available.
     */
    @Test
    @DisplayName("Should allow borrowing when book is not already borrowed")
    void shouldAllowBorrowingWhenBookIsAvailable() throws ConflictException {
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        when(borrowerRepository.findById(1L)).thenReturn(Optional.of(borrower));
        when(ledgerRepository.findByBookIdAndStatus(1L, LedgerStatus.BORROWED))
                .thenReturn(Optional.empty());

        ledgerService.handleLedger(1L, 1L, true);

        verify(ledgerRepository, times(1)).save(any(Ledger.class));
    }

    /**
     * Should throw ConflictException when trying to borrow a book that is already borrowed.
     */
    @Test
    @DisplayName("Should throw exception if book is already borrowed")
    void shouldThrowExceptionIfBookAlreadyBorrowed() {
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        when(borrowerRepository.findById(1L)).thenReturn(Optional.of(borrower));
        when(ledgerRepository.findByBookIdAndStatus(1L, LedgerStatus.BORROWED))
                .thenReturn(Optional.of(ledger));

        assertThrows(ConflictException.class, () -> ledgerService.handleLedger(1L, 1L, true));

        verify(ledgerRepository, never()).save(any());
    }

    /**
     * Should successfully mark a ledger entry as returned when the book is currently borrowed.
     */
    @Test
    @DisplayName("Should allow returning a borrowed book")
    void shouldAllowReturningBook() throws ConflictException {
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        when(borrowerRepository.findById(1L)).thenReturn(Optional.of(borrower));
        when(ledgerRepository.findByBookIdAndStatus(1L, LedgerStatus.BORROWED))
                .thenReturn(Optional.of(ledger));

        ledgerService.handleLedger(1L, 1L, false);

        verify(ledgerRepository, times(1)).save(any(Ledger.class));
    }

    /**
     * Should throw ConflictException when trying to return a book that hasn't been borrowed.
     */
    @Test
    @DisplayName("Should throw exception if book is not borrowed but trying to return")
    void shouldThrowExceptionIfBookNotBorrowedButReturnAttempted() {
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        when(borrowerRepository.findById(1L)).thenReturn(Optional.of(borrower));
        when(ledgerRepository.findByBookIdAndStatus(1L, LedgerStatus.BORROWED))
                .thenReturn(Optional.empty());

        assertThrows(ConflictException.class, () -> ledgerService.handleLedger(1L, 1L, false));

        verify(ledgerRepository, never()).save(any());
    }

    /**
     * Should throw ConflictException when trying to borrow or return a non-existent book.
     */
    @Test
    @DisplayName("Should throw exception when book does not exist")
    void shouldThrowExceptionWhenBookDoesNotExist() {
        when(bookRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ConflictException.class, () -> ledgerService.handleLedger(1L, 1L, true));
        verify(borrowerRepository, never()).findById(any());
        verify(ledgerRepository, never()).save(any());
    }

    /**
     * Should throw ConflictException when the borrower does not exist.
     */
    @Test
    @DisplayName("Should throw exception when borrower does not exist")
    void shouldThrowExceptionWhenBorrowerDoesNotExist() {
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        when(borrowerRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ConflictException.class, () -> ledgerService.handleLedger(1L, 1L, true));
        verify(ledgerRepository, never()).save(any());
    }

    /**
     * Should set status to RETURNED when returning a borrowed book.
     */
    @Test
    @DisplayName("Should set ledger status to RETURNED on return")
    void shouldSetStatusToReturnedOnReturn() throws ConflictException {
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        when(borrowerRepository.findById(1L)).thenReturn(Optional.of(borrower));
        when(ledgerRepository.findByBookIdAndStatus(1L, LedgerStatus.BORROWED)).thenReturn(Optional.of(ledger));

        ledgerService.handleLedger(1L, 1L, false);

        assert ledger.getStatus() == LedgerStatus.RETURNED;
        verify(ledgerRepository, times(1)).save(ledger);
    }
}
