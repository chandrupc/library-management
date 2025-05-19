package com.library.management.service;

import com.library.management.exception.ConflictException;

/**
 * Service interface for managing ledger operations related to borrowing and returning books
 * in the library management system.
 * <p>
 * Provides methods to handle the creation and update of ledger entries based on book borrowing or returning actions.
 * </p>
 *
 * @author Chandru
 * @version 1.0
 * @since 2025-05-19
 */
public interface LedgerService {

    /**
     * Handles ledger operations for borrowing or returning a book.
     *
     * @param bookId     the ID of the book involved in the transaction
     * @param borrowerId the ID of the borrower involved in the transaction
     * @param isBorrow   flag indicating whether the book is being borrowed (true) or returned (false)
     * @throws ConflictException if there is a conflict in ledger status, such as borrowing a book that is already borrowed
     */
    void handleLedger(Long bookId, Long borrowerId, boolean isBorrow) throws ConflictException;
}
